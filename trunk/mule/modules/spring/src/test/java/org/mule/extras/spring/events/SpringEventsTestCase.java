/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extras.spring.events;

import org.mule.MuleManager;
import org.mule.extras.client.MuleClient;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.tck.functional.EventCallback;
import org.mule.transformers.AbstractEventAwareTransformer;
import org.mule.umo.UMOEventContext;
import org.mule.umo.UMOException;
import org.mule.umo.transformer.TransformerException;
import org.mule.util.concurrent.Latch;

import edu.emory.mathcs.backport.java.util.concurrent.CountDownLatch;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringEventsTestCase extends AbstractMuleTestCase
{
    private static final int NUMBER_OF_MESSAGES = 10;
    private static final AtomicInteger eventCounter1 = new AtomicInteger(0);
    private static final AtomicInteger eventCounter2 = new AtomicInteger(0);
    private static ClassPathXmlApplicationContext context;

    protected void doSetUp() throws Exception
    {
        context = new ClassPathXmlApplicationContext(getConfigResources());
        eventCounter1.set(0);
        eventCounter2.set(0);
    }

    protected String getConfigResources()
    {
        return "mule-events-app-context.xml";
    }

    public void testRemovingListeners() throws Exception
    {
        TestSubscriptionEventBean subscriptionBean = (TestSubscriptionEventBean)context.getBean("testSubscribingEventBean1");
        assertNotNull(subscriptionBean);
        MuleEventMulticaster multicaster = (MuleEventMulticaster)context.getBean(AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME);
        assertNotNull(multicaster);

        Latch whenFinished = new Latch();
        subscriptionBean.setEventCallback(new CountingEventCallback(eventCounter1, 1, whenFinished));

        multicaster.removeApplicationListener(subscriptionBean);
        MuleClient client = new MuleClient();
        client.send("vm://event.multicaster", "Test Spring Event", null);

        afterPublishEvent();
        assertEquals(0, eventCounter1.get());

        multicaster.addApplicationListener(subscriptionBean);
        client.send("vm://event.multicaster", "Test Spring Event", null);

        afterPublishEvent();
        assertTrue(whenFinished.await(3000, TimeUnit.MILLISECONDS));        
        assertEquals(1, eventCounter1.get());
        eventCounter1.set(0);

        multicaster.removeAllListeners();
        client.send("vm://event.multicaster", "Test Spring Event", null);

        afterPublishEvent();
        assertEquals(0, eventCounter1.get());
        multicaster.addApplicationListener(subscriptionBean);
        context.refresh();
        subscriptionBean.setEventCallback(null);
    }

    public void testReceivingANonSubscriptionMuleEvent() throws Exception
    {
        TestMuleEventBean bean = (TestMuleEventBean)context.getBean("testNonSubscribingMuleEventBean");
        assertNotNull(bean);

        // register a callback
        Latch whenFinished = new Latch();
        bean.setEventCallback(new CountingEventCallback(eventCounter1, 1, whenFinished));

        MuleClient client = new MuleClient();
        client.send("vm://event.multicaster", "Test Spring Event", null);

        afterPublishEvent();
        assertTrue(whenFinished.await(3000, TimeUnit.MILLISECONDS));        
        assertEquals(1, eventCounter1.get());
    }

    public void testReceivingASpringEvent() throws Exception
    {
        TestApplicationEventBean bean = (TestApplicationEventBean)context.getBean("testEventSpringBean");
        assertNotNull(bean);

        EventCallback callback = new EventCallback()
        {
            public void eventReceived(UMOEventContext context, Object o) throws Exception
            {
                eventCounter1.incrementAndGet();
                assertNull(context);
                assertTrue(o instanceof ContextRefreshedEvent);
            }
        };

        bean.setEventCallback(callback);

        context.publishEvent(new ContextRefreshedEvent(context));

        afterPublishEvent();
        assertEquals(1, eventCounter1.get());
    }

    public void testReceivingAllEvents() throws Exception
    {
        TestAllEventBean bean = (TestAllEventBean)context.getBean("testAllEventBean");
        assertNotNull(bean);

        Latch whenFinished = new Latch();
        bean.setEventCallback(new CountingEventCallback(eventCounter1, 2, whenFinished));

        MuleClient client = new MuleClient();
        client.send("vm://event.multicaster", "Test Spring Event", null);
        context.publishEvent(new ContextRefreshedEvent(context));
        afterPublishEvent();

        assertTrue(whenFinished.await(3000, TimeUnit.MILLISECONDS));        
        assertEquals(2, eventCounter1.get());
    }

    public void testReceivingASubscriptionEvent() throws Exception
    {
        TestSubscriptionEventBean subscriptionBean = (TestSubscriptionEventBean)context.getBean("testSubscribingEventBean1");
        assertNotNull(subscriptionBean);

        Latch whenFinished = new Latch();
        subscriptionBean.setEventCallback(new CountingEventCallback(eventCounter1, 1, whenFinished));

        MuleClient client = new MuleClient();
        client.send("vm://event.multicaster", "Test Spring Event", null);
        afterPublishEvent();

        assertTrue(whenFinished.await(3000, TimeUnit.MILLISECONDS));        
        assertEquals(1, eventCounter1.get());
    }

    public void testReceiveAndPublishEvent() throws Exception
    {
        TestSubscriptionEventBean bean1 = (TestSubscriptionEventBean)context.getBean("testSubscribingEventBean1");
        assertNotNull(bean1);

        EventCallback callback = new EventCallback()
        {
            public void eventReceived(UMOEventContext context, Object o) throws Exception
            {
                eventCounter1.incrementAndGet();
                MuleApplicationEvent returnEvent = new MuleApplicationEvent("Event from a spring bean",
                    "vm://testBean2");
                MuleApplicationEvent e = (MuleApplicationEvent)o;
                e.getApplicationContext().publishEvent(returnEvent);
            }
        };
        bean1.setEventCallback(callback);

        TestSubscriptionEventBean bean2 = (TestSubscriptionEventBean)context.getBean("testSubscribingEventBean2");
        assertNotNull(bean2);

        Latch whenFinished = new Latch();
        bean2.setEventCallback(new CountingEventCallback(eventCounter2, NUMBER_OF_MESSAGES, whenFinished));

        // send asynchronously
        this.doSend("vm://event.multicaster",  "Test Spring Event", NUMBER_OF_MESSAGES);

        assertTrue(whenFinished.await(3000, TimeUnit.MILLISECONDS));        
        assertEquals(NUMBER_OF_MESSAGES, eventCounter1.get());
        assertEquals(NUMBER_OF_MESSAGES, eventCounter2.get());
    }

    public void testPublishOnly() throws Exception
    {
        final MuleApplicationEvent event = new MuleApplicationEvent("Event from a spring bean", "vm://testBean2");

        TestSubscriptionEventBean bean2 = (TestSubscriptionEventBean)context.getBean("testSubscribingEventBean2");
        assertNotNull(bean2);

        Latch whenFinished = new Latch();
        bean2.setEventCallback(new CountingEventCallback(eventCounter1, NUMBER_OF_MESSAGES, whenFinished));

        // publish asynchronously
        this.doPublish(event, NUMBER_OF_MESSAGES);

        assertTrue(whenFinished.await(3000, TimeUnit.MILLISECONDS));        
        assertEquals(NUMBER_OF_MESSAGES, eventCounter1.get());
    }

    public void testPublishWithEventAwareTransformer() throws Exception
    {
        CountDownLatch transformerLatch = new CountDownLatch(1);

        DummyTrans trans = new DummyTrans();
        trans.setLatch(transformerLatch);
        MuleManager.getInstance().registerTransformer(trans);

        MuleApplicationEvent event = new MuleApplicationEvent("Event from a spring bean",
            "vm://testBean2?transformers=dummyTrans");

        TestSubscriptionEventBean bean2 = (TestSubscriptionEventBean)context.getBean("testSubscribingEventBean2");
        assertNotNull(bean2);

        Latch whenFinished = new Latch();
        bean2.setEventCallback(new CountingEventCallback(eventCounter1, 1, whenFinished));

        // publish asynchronously
        this.doPublish(event, 1);

        assertTrue(whenFinished.await(3000, TimeUnit.MILLISECONDS));        
        assertTrue(transformerLatch.await(3000, TimeUnit.MILLISECONDS));
        assertEquals(1, eventCounter1.get());
    }

    protected void afterPublishEvent() throws InterruptedException
    {
        // nothing to do
    }

    protected void doPublish(final ApplicationEvent event, final int count)
    {
        Runnable publisher = new Runnable()
        {
            public void run()
            {
                for (int i = 0; i < count; i++)
                {
                    context.publishEvent(event);
                }
            }
        };

        // execute in background
        Executors.newSingleThreadExecutor().execute(publisher);
    }

    protected void doSend(final String url, final Object payload, final int count)
    {
        Runnable sender = new Runnable()
        {
            public void run()
            {
                try
                {
                    MuleClient client = new MuleClient();
                    for (int i = 0; i < count; i++)
                    {
                        client.send(url, payload, null);
                    }
                }
                catch (UMOException ex)
                {
                    fail(ExceptionUtils.getStackTrace(ex)); 
                }
            }
        };

        // execute in background
        Executors.newSingleThreadExecutor().execute(sender);
    }
    
   
    public static class CountingEventCallback implements EventCallback
    {
        private final AtomicInteger counter;
        private final int count;
        private final CountDownLatch finished;

        public CountingEventCallback(AtomicInteger counter, int count, CountDownLatch whenFinished)
        {
            super();
            this.counter = counter;
            this.count = count;
            this.finished = whenFinished;
        }

        public void eventReceived(UMOEventContext context, Object o) throws Exception
        {
            if (counter.incrementAndGet() == count && finished != null)
            {
                finished.countDown();
            }
        }
    }

    public static class DummyTrans extends AbstractEventAwareTransformer
    {
        private CountDownLatch latch;

        public DummyTrans()
        {
            setName("dummyTrans");
        }

        // @Override
        public Object clone() throws CloneNotSupportedException
        {
            // we MUST share the latch for this test since we obviously want to wait for it.
            return super.clone();
        }

        public CountDownLatch getLatch()
        {
            return latch;
        }

        public void setLatch(CountDownLatch latch)
        {
            this.latch = latch;
        }

        public Object transform(Object src, String encoding, UMOEventContext context)
            throws TransformerException
        {
            assertNotNull(context);
            latch.countDown();
            return src;
        }
    }

}
