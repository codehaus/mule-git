/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.jdbc.sqlstrategy;

/**
 * Implements strategy for handling normal select statements + acks.  
 * 
 */

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.transaction.Transaction;
import org.mule.api.transport.MessageAdapter;
import org.mule.transaction.TransactionCoordination;
import org.mule.transport.jdbc.JdbcConnector;
import org.mule.transport.jdbc.JdbcUtils;
import org.mule.util.ArrayUtils;

public  class SelectSQLStrategy 
    implements SQLStrategy
{

	protected static Logger logger = Logger.getLogger(SelectSQLStrategy.class);

	
	
    public MuleMessage executeStatement(JdbcConnector connector,
			ImmutableEndpoint endpoint,MuleEvent event,long timeout) throws Exception
    {
            
        logger.debug("Trying to receive a message with a timeout of " + timeout);
        
        String[] stmts = connector.getReadAndAckStatements(endpoint);
        String readStmt = stmts[0];
        String ackStmt = stmts[1];
        List readParams = new ArrayList();
        List ackParams = new ArrayList();
        readStmt = connector.parseStatement(readStmt, readParams);
        ackStmt = connector.parseStatement(ackStmt, ackParams);

        Connection con = null;
        long t0 = System.currentTimeMillis();
        Transaction tx  = TransactionCoordination.getInstance().getTransaction();
        try
        {
            con = connector.getConnection();
            if (timeout < 0)
            {
                timeout = Long.MAX_VALUE;
            }
            Object result;
            do
            {
                Object[] params = connector.getParams(endpoint, readParams,
                    event!=null ? event.getMessage() : null,
                    endpoint.getEndpointURI().getAddress());
                if (logger.isDebugEnabled())
                {
                    logger.debug("SQL QUERY: " + readStmt + ", params = " + ArrayUtils.toString(params));
                }
                result = connector.getQueryRunner().query(con, readStmt, params, connector.getResultSetHandler());
                if (result != null)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("Received: " + result);
                    }
                    break;
                }
                long sleep = Math.min(connector.getPollingFrequency(),
                                      timeout - (System.currentTimeMillis() - t0));
                if (sleep > 0)
                {
                    if (logger.isDebugEnabled())
                    {
                        logger.debug("No results, sleeping for " + sleep);
                    }
                    Thread.sleep(sleep);
                }
                else
                {
                    logger.debug("Timeout");
                    JdbcUtils.rollbackAndClose(con);
                    return null;
                }
            }
            while (true);
            if (ackStmt != null)
            {
                Object[] params = connector.getParams(endpoint, ackParams, result, ackStmt);
                if (logger.isDebugEnabled())
                {
                    logger.debug("SQL UPDATE: " + ackStmt + ", params = " + ArrayUtils.toString(params));
                }
                int nbRows = connector.getQueryRunner().update(con, ackStmt, params);
                if (nbRows != 1)
                {
                    logger.warn("Row count for ack should be 1 and not " + nbRows);
                }
            }
            MessageAdapter msgAdapter = connector.getMessageAdapter(result);
            MuleMessage message = new DefaultMuleMessage(msgAdapter);
            if (tx == null)
            {
                JdbcUtils.commitAndClose(con);
            }
            
            return message;
        }
        catch (Exception e)
        {
            if (tx == null)
            {
                JdbcUtils.rollbackAndClose(con);
            }
            throw e;
        }

    }


}
