/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.processor;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.lifecycle.Disposable;
import org.mule.api.lifecycle.Initialisable;
import org.mule.api.lifecycle.InitialisationException;
import org.mule.api.lifecycle.Lifecycle;
import org.mule.api.lifecycle.Startable;
import org.mule.api.lifecycle.Stoppable;
import org.mule.api.processor.InterceptingMessageProcessor;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.processor.MessageProcessorBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Constructs a chain of {@link MessageProcessor}'s and wraps the invocation of the
 * chain in a composite MessageProcessor. Both MessageProcessor's and
 * InterceptingMessageProcessor's can be chained together arbitrarily in a single
 * chain. InterceptingMessageProcessor's simply intercept the next MessageProcessor
 * in the chain. When other non-intercepting MessageProcessor's are used an adapter
 * is used internally to chain the MessageProcessor with the next in the chain.
 * </p>
 * <p>
 * The MessageProcessor instance that this builder builds can be nested in other
 * chains as required.
 * </p>
 */
public class ChainMessageProcessorBuilder implements MessageProcessorBuilder
{

    protected List<MessageProcessor> chain = new ArrayList<MessageProcessor>();

    public MessageProcessor build()
    {
        if (chain.isEmpty())
        {
            return new NullMessageProcesser();
        }

        InterceptingMessageProcessor first = createInterceptngMessageProcessor(chain.get(0));;
        MessageProcessor composite = new ChainedCompositeMessageProcessor(first, chain);
        InterceptingMessageProcessor current = first;

        for (int i = 1; i < chain.size(); i++)
        {
            InterceptingMessageProcessor mp = createInterceptngMessageProcessor(chain.get(i));
            current.setNext(mp);
            current = mp;
        }
        return composite;
    }

    private InterceptingMessageProcessor createInterceptngMessageProcessor(MessageProcessor processor)
    {
        if (processor instanceof InterceptingMessageProcessor)
        {
            return (InterceptingMessageProcessor) processor;
        }
        else
        {
            return new InterceptingMessageProcesserAdaptor(processor);
        }
    }

    public ChainMessageProcessorBuilder chain(MessageProcessor... processors)
    {
        for (MessageProcessor messageProcessor : processors)
        {
            chain.add(messageProcessor);
        }
        return this;
    }

    // TODO BL-23 Temporary until inbound chain customization is implemented
    @Deprecated
    public void replaceMessageProcessor(Class processorClass, MessageProcessor replacement)
    {
        for (MessageProcessor processor : chain)
        {
            if (processor.getClass().equals(processorClass))
            {
                processor = replacement;
            }
        }
    }

    static class InterceptingMessageProcesserAdaptor implements InterceptingMessageProcessor
    {

        private MessageProcessor delegate;
        private MessageProcessor next;

        public InterceptingMessageProcesserAdaptor(MessageProcessor mp)
        {
            this.delegate = mp;
        }

        public MuleEvent process(MuleEvent event) throws MuleException
        {
            MuleEvent delegateResult = delegate.process(event);
            if (next != null)
            {
                return next.process(delegateResult);
            }
            else
            {
                return delegateResult;
            }
        }

        public void setNext(MessageProcessor next)
        {
            this.next = next;
        }
    }

    /**
     * Builder needs to return a composite rather than the first MessageProcessor in
     * the chain. This is so that if this chain is nested in another chain
     */
    static class ChainedCompositeMessageProcessor implements MessageProcessor, Lifecycle
    {
        private MessageProcessor firstInChain;
        private List<MessageProcessor> processors;

        public ChainedCompositeMessageProcessor(MessageProcessor firstInChain,
                                                List<MessageProcessor> allProcessors)
        {
            this.firstInChain = firstInChain;
            this.processors = allProcessors;
        }

        public MuleEvent process(MuleEvent event) throws MuleException
        {
            return firstInChain.process(event);
        }

        public void initialise() throws InitialisationException
        {
            for (MessageProcessor processor : processors)
            {
                if (processor instanceof Initialisable)
                {
                    ((Initialisable) processor).initialise();
                }
            }
        }

        public void start() throws MuleException
        {
            for (MessageProcessor processor : processors)
            {
                if (processor instanceof Startable)
                {
                    ((Startable) processor).start();
                }
            }
        }

        public void stop() throws MuleException
        {
            for (MessageProcessor processor : processors)
            {
                if (processor instanceof Stoppable)
                {
                    ((Stoppable) processor).stop();
                }
            }
        }

        public void dispose()
        {
            for (MessageProcessor processor : processors)
            {
                if (processor instanceof Disposable)
                {
                    ((Disposable) processor).dispose();
                }
            }
        }
    }

    static class NullMessageProcesser implements MessageProcessor
    {
        public MuleEvent process(MuleEvent event) throws MuleException
        {
            return event;
        }

    }

}
