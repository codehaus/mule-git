/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.examples.loanbroker;

import org.mule.MuleManager;
import org.mule.config.ConfigurationBuilder;
import org.mule.config.builders.MuleXmlConfigurationBuilder;
import org.mule.config.i18n.Message;
import org.mule.examples.loanbroker.messages.Customer;
import org.mule.examples.loanbroker.messages.CustomerQuoteRequest;
import org.mule.extras.client.MuleClient;
import org.mule.umo.UMOException;
import org.mule.umo.UMOMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Runs the LoanBroker example application.
 */
public abstract class AbstractLoanBrokerApp
{
    private List customers = new ArrayList();
    private MuleClient client = null;
    private String config;

    public AbstractLoanBrokerApp(String config) throws Exception
    {
        this.config = config;
        init();
    }

    protected void init() throws Exception
    {
        ConfigurationBuilder builder = getConfigBuilder();
        builder.configure(config);

        client = new MuleClient();

        customers.add(new Customer("Jenson Button", 123));
        customers.add(new Customer("Michael Schumacker", 456));
        customers.add(new Customer("Juan Pablo Montoya", 789));
        customers.add(new Customer("David Colthard", 101));
        customers.add(new Customer("Rubens Barrichello", 112));
        customers.add(new Customer("Mark Webber", 131));
        customers.add(new Customer("Takuma Sato", 415));
        customers.add(new Customer("Kimi Raikkonen", 161));
        customers.add(new Customer("Ralf Schumacher", 718));
        customers.add(new Customer("Jarno Trulli", 192));
    }

    protected ConfigurationBuilder getConfigBuilder() throws UMOException
    {
        return new MuleXmlConfigurationBuilder();
    }
    
    protected void dispose() throws Exception
    {
        MuleManager.getInstance().dispose();
    }

    protected void run(boolean synchronous) throws Exception
    {
        int response = 0;
        while (response != 'q')
        {
            System.out.println("\n" + new Message("loanbroker-example", 41).getMessage());

            response = readCharacter();

            switch (response)
            {
                case '1' :
                {
                    CustomerQuoteRequest request = getRequestFromUser();
                    request(request, synchronous);
                    break;
                }

                case '2' :
                {
                    sendRandomRequests(100, synchronous);
                    break;
                }

                case '3' :
                {
                    System.out.println(new Message("loanbroker-example", 22).getMessage());
                    int number = readInt();
                    if (number < 1)
                    {
                        System.out.println(new Message("loanbroker-example", 23).getMessage());
                    }
                    else
                    {
                        sendRandomRequests(number, synchronous);
                    }
                    break;
                }

                case 'q' :
                {
                    System.out.println(new Message("loanbroker-example", 14).getMessage());
                    dispose();
                    System.exit(0);
                }

                default :
                {
                    System.out.println(new Message("loanbroker-example", 15).getMessage());
                }
            }
        }
    }
    
    public CustomerQuoteRequest createRequest()
    {
        int index = new Double(Math.random() * 10).intValue();
        Customer c = (Customer)customers.get(index);

        return new CustomerQuoteRequest(c, getRandomAmount(), getRandomDuration());
    }

    protected static CustomerQuoteRequest getRequestFromUser() throws IOException
    {
        byte[] buf = new byte[128];
        System.out.print(new Message("loanbroker-example", 16).getMessage());
        System.in.read(buf);
        String name = new String(buf).trim();
        System.out.print(new Message("loanbroker-example", 17).getMessage());
        buf = new byte[16];
        System.in.read(buf);
        String amount = new String(buf).trim();
        System.out.print(new Message("loanbroker-example", 18).getMessage());
        buf = new byte[16];
        System.in.read(buf);
        String duration = new String(buf).trim();

        int d = 0;
        try
        {
            d = Integer.parseInt(duration);
        }
        catch (NumberFormatException e)
        {
            System.out.println(new Message("loanbroker-example", 19, duration).getMessage());
            d = getRandomDuration();
        }

        double a = 0;
        try
        {
            a = Double.valueOf(amount).doubleValue();
        }
        catch (NumberFormatException e)
        {
            System.out.println(new Message("loanbroker-example", 20, amount).getMessage());
            a = getRandomAmount();
        }

        Customer c = new Customer(name, getRandomSsn());
        CustomerQuoteRequest request = new CustomerQuoteRequest(c, a, d);
        return request;
    }

    public void request(CustomerQuoteRequest request, boolean sync) throws Exception
    {
        if (!sync)
        {
            client.dispatch("vm://customer.requests", request, null);
            System.out.println(new Message("loanbroker-example", 42).getMessage());
            // let the request catch up
            Thread.sleep(1500);
        }
        else
        {
            UMOMessage result = client.send("vm://customer.requests", request, null);
            if (result == null)
            {
                System.out.println(new Message("loanbroker-example", 12).getMessage());
            }
            else
            {
                System.out.println(new Message("loanbroker-example", 13, result.getPayload()).getMessage());
            }
        }
    }

    public void requestDispatch(int number, String endpoint) throws Exception
    {
        for (int i = 0; i < number; i++)
        {
            client.dispatch(endpoint, createRequest(), null);
        }
    }

    public List requestSend(int number, String endpoint) throws Exception
    {
        List results = new ArrayList(number);
        UMOMessage result;
        for (int i = 0; i < number; i++)
        {
            result = client.send(endpoint, createRequest(), null);
            if (result != null)
            {
                results.add(result.getPayload());
            }
        }
        return results;
    }

    protected void sendRandomRequests(int number, boolean synchronous) throws Exception
    {
        if (synchronous)
        {
            List list = this.requestSend(number, "vm://customer.requests");
            int i = 1;
            for (Iterator iterator = list.iterator(); iterator.hasNext(); i++)
            {
                System.out.println(new Message("loanbroker-example", 24, String.valueOf(i), iterator.next().toString()).getMessage());
            }
        }
        else
        {
            this.requestDispatch(number, "vm://customer.requests");
        }
    }

    protected static int readCharacter() throws IOException
    {
        byte[] buf = new byte[16];
        System.in.read(buf);
        return buf[0];
    }

    protected static String readString() throws IOException
    {
        byte[] buf = new byte[80];
        System.in.read(buf);
        return new String(buf).trim();
    }

    protected static int readInt() throws IOException
    {
        try
        {
            return Integer.parseInt(readString());
        }
        catch (NumberFormatException nfex)
        {
            return 0;
        }
    }

    protected static double getRandomAmount()
    {
        return Math.round(Math.random() * 18000);
    }

    protected static int getRandomDuration()
    {
        return new Double(Math.random() * 60).intValue();
    }

    protected static int getRandomSsn()
    {
        return new Double(Math.random() * 6000).intValue();
    }
}

