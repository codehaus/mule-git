/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.examples.loanbroker.esb;

import org.mule.config.i18n.Message;
import org.mule.examples.loanbroker.AbstractLoanBrokerApp;

import java.io.IOException;

import org.apache.activemq.broker.BrokerService;

/**
 * Runs the LoanBroker ESB example application.
 */

public class LoanBrokerApp extends AbstractLoanBrokerApp
{
    private BrokerService msgBroker = null;

    public LoanBrokerApp(String config) throws Exception
    {
        super(config);
    }

    public void init() throws Exception
    {
        // Start up the ActiveMQ message broker.
        msgBroker = new BrokerService();
        try
        {
            msgBroker.addConnector("tcp://localhost:61616");
            msgBroker.start();
        } 
        finally
        {
            if (msgBroker != null)
            {
                msgBroker.stop();
            }
        }
        
        super.init();
    }

    public void dispose() throws Exception
    {
        super.dispose();
        if (msgBroker != null)
        {
            msgBroker.stop();
        }
    }

    public static void main(String[] args) throws Exception
    {
        LoanBrokerApp loanBrokerApp = null;
        loanBrokerApp = new LoanBrokerApp(getInteractiveConfig());
        loanBrokerApp.run(false);
    }

    protected static String getInteractiveConfig() throws IOException
    {
        int response = 0;
        
        System.out.println("******************\n"
            + new Message("loanbroker-example", 30).getMessage()
            + "\n******************");
        response = readCharacter();
        if (response == '1')
        {
            System.out.println(new Message("loanbroker-example", 31).getMessage());
            return "loan-broker-esb-mule-config.xml";
        }
        else
        {
            System.out.println(new Message("loanbroker-example", 33).getMessage());
            return "loan-broker-esb-mule-config-with-ejb-container.xml";
        }
    }
}
