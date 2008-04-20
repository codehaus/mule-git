/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.quartz.config;

import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.config.spring.parsers.collection.ChildMapDefinitionParser;
import org.mule.config.spring.parsers.collection.ChildSingletonMapDefinitionParser;
import org.mule.config.spring.parsers.generic.MuleOrphanDefinitionParser;
import org.mule.config.spring.parsers.generic.ChildDefinitionParser;
import org.mule.config.spring.parsers.generic.ParentDefinitionParser;
import org.mule.config.spring.parsers.specific.DataObjectDefinitionParser;
import org.mule.config.spring.parsers.specific.endpoint.EndpointRefParser;
import org.mule.config.spring.factories.OutboundEndpointFactoryBean;
import org.mule.config.spring.factories.InboundEndpointFactoryBean;
import org.mule.config.spring.factories.GlobalEndpointFactoryBean;
import org.mule.endpoint.EndpointURIEndpointBuilder;
import org.mule.transport.quartz.QuartzConnector;
import org.mule.transport.quartz.jobs.EventGeneratorJobConfig;
import org.mule.transport.quartz.jobs.EndpointPollingJobConfig;
import org.mule.transport.quartz.jobs.ScheduledDispatchJobConfig;
import org.mule.transport.quartz.jobs.CustomJobConfig;
import org.mule.transport.quartz.jobs.CustomJobFromMessageConfig;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.endpoint.OutboundEndpoint;

/**
 * Registers Bean Definition Parsers for the "quartz" namespace
 */
public class QuartzNamespaceHandler extends AbstractMuleNamespaceHandler
{

    public void init()
    {
        registerStandardTransportEndpoints(QuartzConnector.QUARTZ, new String[]{"jobName"});
        registerMuleBeanDefinitionParser("connector", new MuleOrphanDefinitionParser(QuartzConnector.class, true)).addAlias("scheduler", "quartzScheduler");
        // note that we use the singular (factoryProperty) for the setter so that we auto-detect a collection
        registerBeanDefinitionParser("factory-property", new ChildSingletonMapDefinitionParser("factoryProperty"));
        registerBeanDefinitionParser("factory-properties", new ChildMapDefinitionParser("factoryProperty"));

        registerBeanDefinitionParser("event-generator-job", new ChildDefinitionParser(QuartzConnector.PROPERTY_JOB_CONFIG, EventGeneratorJobConfig.class));
        registerBeanDefinitionParser("endpoint-polling-job", new ChildDefinitionParser(QuartzConnector.PROPERTY_JOB_CONFIG, EndpointPollingJobConfig.class));
        registerBeanDefinitionParser("scheduled-dispatch-job", new ChildDefinitionParser(QuartzConnector.PROPERTY_JOB_CONFIG, ScheduledDispatchJobConfig.class));
        registerBeanDefinitionParser("custom-job", new ChildDefinitionParser(QuartzConnector.PROPERTY_JOB_CONFIG, CustomJobConfig.class));
        registerBeanDefinitionParser("custom-job-from-message", new ChildDefinitionParser(QuartzConnector.PROPERTY_JOB_CONFIG, CustomJobFromMessageConfig.class));

        ParentDefinitionParser parser = new ParentDefinitionParser();
        parser.addAlias("address", "endpointRef");
        parser.addAlias("ref", "endpointRef");
        registerBeanDefinitionParser("job-endpoint", new EndpointRefParser("endpointRef"));

        //registerBeanDefinitionParser("job-endpoint", new EndpointRefDefinitionParser());

        registerBeanDefinitionParser("payload", new DataObjectDefinitionParser("payload"));
    }

    //@Override
    protected Class getInboundEndpointFactoryBeanClass()
    {
        return QuartzInboundEndpointFactoryBean.class;
    }

    //@Override
    protected Class getGlobalEndpointFactoryBeanClass()
    {
        return QuartzGlobalEndpointFactoryBean.class;
    }

    //@Override
    protected Class getOutboundEndpointFactoryBeanClass()
    {
        return QuartzOutboundEndpointFactoryBean.class;
    }

    private class QuartzInboundEndpointFactoryBean extends InboundEndpointFactoryBean
    {
        private JobConfig jobConfig;

        //@Override
        public Object doGetObject() throws Exception
        {
            InboundEndpoint in = (InboundEndpoint)super.doGetObject();
            in.getProperties().put(QuartzConnector.PROPERTY_JOB_CONFIG, jobConfig);
            return in;
        }

        public JobConfig getJobConfig()
        {
            return jobConfig;
        }

        public void setJobConfig(JobConfig jobConfig)
        {
            this.jobConfig = jobConfig;
        }
    }

    private class QuartzOutboundEndpointFactoryBean extends OutboundEndpointFactoryBean
    {
        private JobConfig jobConfig;

        //@Override
        public Object doGetObject() throws Exception
        {
            OutboundEndpoint in = (OutboundEndpoint)super.doGetObject();
            in.getProperties().put(QuartzConnector.PROPERTY_JOB_CONFIG, jobConfig);
            return in;
        }

        public JobConfig getJobConfig()
        {
            return jobConfig;
        }

        public void setJobConfig(JobConfig jobConfig)
        {
            this.jobConfig = jobConfig;
        }
    }

    public static class QuartzGlobalEndpointFactoryBean extends GlobalEndpointFactoryBean
    {
        private JobConfig jobConfig;

        public Object doGetObject() throws Exception
        {
            EndpointURIEndpointBuilder in = (EndpointURIEndpointBuilder)super.doGetObject();
            in.setProperty(QuartzConnector.PROPERTY_JOB_CONFIG, jobConfig);
            return in;
        }

        public JobConfig getJobConfig()
        {
            return jobConfig;
        }

        public void setJobConfig(JobConfig jobConfig)
        {
            this.jobConfig = jobConfig;
        }
    }

}
