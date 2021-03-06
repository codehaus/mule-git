/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.providers.jms.transactions;

import org.mule.config.MuleProperties;
import org.mule.providers.jms.functional.AbstractJmsFunctionalTestCase;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.transaction.TransactionCoordination;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOTransaction;

import javax.jms.Message;
import javax.jms.TextMessage;

import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;

/**
 * <code>AbstractJmsTransactionFunctionalTest</code> is a base class for all JMS
 * based functional tests with or without transactions.
 *
 * TODO Tests for BEGIN_OR_JOIN, ALWAYS_JOIN, JOIN_IF_POSSIBLE
 */
public abstract class AbstractJmsTransactionFunctionalTest extends AbstractJmsFunctionalTestCase
{
    public static final int SEND_NOT_TRANSACTED = 0x01;
    public static final int SEND_TRANSACTED_ALWAYS = 0x02;
    public static final int SEND_TRANSACTED_IF_POSSIBLE_WITH_TRANSACTION = 0x04;
    public static final int SEND_TRANSACTED_IF_POSSIBLE_WITHOUT_TRANSACTION = 0x08;
    public static final int SEND_TRANSACTED_ROLLBACK = 0x10;
    public static final int CLEANUP = 0x20;
    public static final int TRANSACTED_REDELIVERY_TO_DL_DESTINATION = 0x40;
    public static final int ALL = 0xffff;

    /**
     * A callback method will set this with the current tranaction while proceesing the event.
     * The unit test can later verify whether the transaction has been committed or rolled back.
     */
    protected volatile UMOTransaction currentTx;
    protected int exclusionFlag = 0;

    protected void exclude(int flag)
    {
        exclusionFlag = flag;
    }

    protected boolean notExcluded(int flag)
    {
        boolean excluded = (flag & exclusionFlag) != 0;
        if (excluded)
        {
            logger.warn("Excluding this test");
        }
        return ! excluded;
    }

    protected String getConfigResources()
    {
        return "jms-transaction.xml";
    }

    protected void doSetUp() throws Exception
    {
        super.doSetUp();
        currentTx = null;
    }

    protected void doTearDown() throws Exception
    {
        TransactionCoordination.getInstance().unbindTransaction(
            TransactionCoordination.getInstance().getTransaction());

        super.doTearDown();

        assertNull("There should be no transaction associated with this thread",
                   TransactionCoordination.getInstance().getTransaction());
    }

    public void testSendNotTransacted() throws Exception
    {
        EventCallback callback = new EventCallback()
        {
            public synchronized void eventReceived(UMOEventContext context, Object component) throws Exception
            {
                // Verify no transaction.
                currentTx = context.getCurrentTransaction();
                assertNull(currentTx);

                callbackCalled.countDown();
            }
        };

        FunctionalTestComponent ftc = lookupTestComponent("transactionTestModel", "component1");
        ftc.setEventCallback(callback);

        send(TEST_MESSAGE, "component1In");

        assertTrue(callbackCalled.await(LOCK_TIMEOUT, TimeUnit.MILLISECONDS));
        assertEquals(TEST_MESSAGE, ftc.getLastReceivedMessage());

        // Verify no transaction.
        assertNull(currentTx);

        assertEquals(TEST_MESSAGE_RESPONSE, receiveTextMessage("component1Out"));
    }

    public void testSendTransactedAlways() throws Exception
    {
        EventCallback callback = new EventCallback()
        {
            public synchronized void eventReceived(UMOEventContext context, Object component) throws Exception
            {
                // Verify transaction has begun.
                currentTx = context.getCurrentTransaction();
                assertNotNull(currentTx);
                assertTrue(currentTx.isBegun());

                callbackCalled.countDown();
            }
        };

        FunctionalTestComponent ftc = lookupTestComponent("transactionTestModel", "component2");
        ftc.setEventCallback(callback);

        send(TEST_MESSAGE, "component2In");

        assertTrue(callbackCalled.await(LOCK_TIMEOUT, TimeUnit.MILLISECONDS));
        assertEquals(TEST_MESSAGE, ftc.getLastReceivedMessage());

        assertEquals(TEST_MESSAGE_RESPONSE, receiveTextMessage("component1Out"));

        // Verify transaction has committed.
        assertTrue(currentTx.isBegun());
        // TODO for some reason, it takes a while for committed flag on the tx to update
        Thread.sleep(1000);
        assertTrue(currentTx.isCommitted());

    }

    public void testSendTransactedRollback() throws Exception
    {
        EventCallback callback = new EventCallback()
        {
            public synchronized void eventReceived(UMOEventContext context, Object component) throws Exception
            {
                // Verify transaction has begun.
                currentTx = context.getCurrentTransaction();
                assertNotNull(currentTx);
                assertTrue(currentTx.isBegun());

                // Mark the transaction for rollback.
                logger.info("@@@@ Rolling back transaction @@@@");
                currentTx.setRollbackOnly();

                callbackCalled.countDown();
            }
        };

        FunctionalTestComponent ftc = lookupTestComponent("transactionTestModel", "component2");
        ftc.setEventCallback(callback);

        send(TEST_MESSAGE, "component2In");

        assertTrue(callbackCalled.await(LOCK_TIMEOUT, TimeUnit.MILLISECONDS));
        assertEquals(TEST_MESSAGE, ftc.getLastReceivedMessage());

        // Verify transaction has rolled back.
        assertTrue(currentTx.isBegun());
        // TODO for some reason, it takes a while for committed flag on the tx to update
        Thread.sleep(1000);
        assertTrue(currentTx.isRolledBack());

        // Verify outbound message did _not_ get delivered.
        assertNull("Transaction was rolled back, but message got delivered anyways.",
                   receive("component2Out", 1000));
    }

    public void testTransactedRedeliveryToDLDestination() throws Exception
    {
        EventCallback callback = new EventCallback()
        {
            public synchronized void eventReceived(UMOEventContext context, Object component) throws Exception
            {
                // Verify transaction has begun.
                currentTx = context.getCurrentTransaction();
                assertNotNull(currentTx);
                assertTrue(currentTx.isBegun());

                // Mark the transaction for rollback.
                logger.info("@@@@ Rolling back transaction @@@@");
                currentTx.setRollbackOnly();

                callbackCalled.countDown();
            }
        };

        FunctionalTestComponent ftc = lookupTestComponent("transactionTestModel", "component3");
        ftc.setEventCallback(callback);

        send(TEST_MESSAGE, "component3In");

        assertTrue(callbackCalled.await(LOCK_TIMEOUT, TimeUnit.MILLISECONDS));
        assertEquals(TEST_MESSAGE, ftc.getLastReceivedMessage());

        // Verify transaction has rolled back.
        assertTrue(currentTx.isBegun());
        // TODO for some reason, it takes a while for committed flag on the tx to update
        Thread.sleep(1000);
        assertTrue(currentTx.isRolledBack());

        // Verify outbound message did _not_ get delivered.
        assertNull("Transaction was rolled back, but message got delivered anyways.",
                   receive("component3Out", 1000));

        // Verify message got sent to dead letter queue instead.
        Message dl = receive("dead.letter");
        assertNotNull(dl);
        assertTrue(dl instanceof TextMessage);
        assertEquals(TEST_MESSAGE_RESPONSE, ((TextMessage)dl).getText());

        String dest = dl.getStringProperty(MuleProperties.MULE_ENDPOINT_PROPERTY);
        assertNotNull(dest);
        assertEquals("jms://dead.letter", dest);
    }












//    public void testCleanup() throws Exception
//    {
//        assertNull("There should be no transaction associated with this thread",
//            TransactionCoordination.getInstance().getTransaction());
//    }
//
//    protected void addResultListener(String dest, final CountDownLatch countDown) throws JMSException
//    {
//        MessageConsumer mc;
//        // check replyTo
//        if (useTopics())
//        {
//            mc = JmsTestUtils.getTopicSubscriber((TopicConnection)connection, dest);
//        }
//        else
//        {
//            mc = JmsTestUtils.getQueueReceiver((QueueConnection)connection, dest);
//        }
//        mc.setMessageListener(new MessageListener()
//        {
//            public void onMessage(Message message)
//            {
//                currentMsg = message;
//                if (countDown != null)
//                {
//                    countDown.countDown();
//                }
//            }
//        });
//    }
//
//    public abstract UMOTransactionFactory getTransactionFactory();
//
//    public class RollbackExceptionListener extends DefaultExceptionStrategy
//    {
//        private Latch exceptionStrategyCalled;
//
//        public RollbackExceptionListener(Latch exceptionStrategyCalled)
//        {
//            this.exceptionStrategyCalled = exceptionStrategyCalled;
//        }
//
//        public RollbackExceptionListener(Latch exceptionStrategyCalled, UMOEndpointURI deadLetter)
//            throws UMOException
//        {
//            this(exceptionStrategyCalled);
//            UMOEndpoint ep = managementContext.getRegistry().createEndpointFromUri(deadLetter, UMOEndpoint.ENDPOINT_TYPE_SENDER);
//            // lets include dispatch to the deadLetter queue in the sme tx.
//            ep.setTransactionConfig(new MuleTransactionConfig());
//            ep.getTransactionConfig().setAction(UMOTransactionConfig.ACTION_JOIN_IF_POSSIBLE);
//            super.addEndpoint(ep);
//        }
//
//        public void handleMessagingException(UMOMessage message, Throwable t)
//        {
//            logger.debug("@@@@ ExceptionHandler Called @@@@");
//            if (t instanceof MessageRedeliveredException)
//            {
//                countDown.countDown();
//                try
//                {
//                    // MessageRedeliveredException mre =
//                    // (MessageRedeliveredException)t;
//                    Message msg = (Message)message.getPayload();
//
//                    assertNotNull(msg);
//                    assertTrue(msg.getJMSRedelivered());
//                    assertTrue(msg instanceof TextMessage);
//                    // No need to commit transaction as the Tx template will
//                    // auto matically commit by default
//                    super.handleMessagingException(message, t);
//                }
//                catch (Exception e)
//                {
//                    fail(e.getMessage());
//                }
//            }
//            else
//            {
//                logger.error(ExceptionUtils.getFullStackTrace(t));
//                fail(t.getMessage());
//            }
//            super.handleMessagingException(message, t);
//        }
//
//        public void handleException(Throwable t)
//        {
//            // TODO is this really supposed to be empty?
//        }
//    }

//    public void testTransactedRedeliveryToDLDestination() throws Exception
//    {
//        // there are 2 check points for each message delivered, so
//        // the message will be delivered twice before this countdown will release
//        final int countDownInitialCount = 4;
//        final CountDownLatch countDown = new CountDownLatch(countDownInitialCount);
//
//        // setup the component and start Mule
//        UMODescriptor descriptor = getTestDescriptor("testComponent", FunctionalTestComponent.class.getName());
//
//        EventCallback callback = new EventCallback()
//        {
//            public synchronized void eventReceived(UMOEventContext context, Object component) throws Exception
//            {
//                callbackCalled = true;
//                currentTx = context.getCurrentTransaction();
//                assertNotNull(currentTx);
//                assertTrue(currentTx.isBegun());
//                logger.debug("@@@@ Rolling back transaction @@@@");
//                currentTx.setRollbackOnly();
//                countDown.countDown();
//            }
//        };
//
////        initialiseComponent(descriptor, UMOTransactionConfig.ACTION_ALWAYS_BEGIN, callback);
//
//        addResultListener(getDeadLetterDest().getAddress(), countDown);
//
//        JmsConnector umoCnn = (JmsConnector)managementContext.getRegistry().lookupConnector(CONNECTOR_NAME);
//
//        // After redelivery retry the message and then fail
//        umoCnn.setMaxRedelivery(1);
//
//        // Set the test Exception strategy
//        umoCnn.setExceptionListener(new RollbackExceptionListener(countDown, getDeadLetterDest()));
//
//        // Start the server
//        managementContext.start();
//
//        // Send a test message firstso that it is there when the component is
//        // started
//        send(DEFAULT_MESSAGE, false, Session.AUTO_ACKNOWLEDGE);
//
//        afterInitialise();
//        countDown.await(LOCK_WAIT, TimeUnit.MILLISECONDS);
//        assertTrue("Only " + (countDownInitialCount - countDown.getCount()) + " of " + countDownInitialCount
//                   + " checkpoints hit", countDown.getCount() == 0);
//
//        assertNotNull(currentMsg);
//        logger.debug(currentMsg);
//        String dest = currentMsg.getStringProperty(MuleProperties.MULE_ENDPOINT_PROPERTY);
//        assertNotNull(dest);
//        assertEquals(getDeadLetterDest().getUri().toString(), dest);
//        assertTrue(callbackCalled);
//
//        // Make sure the message isn't on the queue
//        assertNull(receive(getInDest().getAddress(), 2000));
//    }
}
