/*
 * $Header$
 * $Revision$
 * $Date$
 * ------------------------------------------------------------------------------------------------------
 *
 * Copyright (c) SymphonySoft Limited. All rights reserved.
 * http://www.symphonysoft.com
 *
 * The software in this package is published under the terms of the BSD
 * style license a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.mule.providers.jdbc;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.mule.impl.MuleMessage;
import org.mule.providers.TransactedPollingMessageReceiver;
import org.mule.transaction.TransactionCoordination;
import org.mule.umo.UMOComponent;
import org.mule.umo.UMOMessage;
import org.mule.umo.UMOTransaction;
import org.mule.umo.endpoint.UMOEndpoint;
import org.mule.umo.lifecycle.InitialisationException;
import org.mule.umo.provider.UMOConnector;
import org.mule.umo.provider.UMOMessageAdapter;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Guillaume Nodet
 * @version $Revision$
 */
public class JdbcMessageReceiver extends TransactedPollingMessageReceiver {

	private JdbcConnector connector;
	private String readStmt;
	private String ackStmt;
	private List readParams;
	private List ackParams;
	
	public JdbcMessageReceiver(UMOConnector connector,
                               UMOComponent component,
                               UMOEndpoint endpoint,
                               String readStmt,
                               String ackStmt) throws InitialisationException {
        super(connector, component, endpoint, new Long(((JdbcConnector) connector).getPollingFrequency()));
        this.receiveMessagesInTransaction = false;
        this.connector = (JdbcConnector) connector;

        this.readParams = new ArrayList();
        this.readStmt = JdbcUtils.parseStatement(readStmt, this.readParams);
        this.ackParams = new ArrayList();
        this.ackStmt = JdbcUtils.parseStatement(ackStmt, this.ackParams);
	}
	
	public void processMessage(Object message) throws Exception {
		Connection con = null;
		UMOTransaction tx = TransactionCoordination.getInstance().getTransaction(); 
		try {
			con = this.connector.getConnection();
			if (this.ackStmt != null) {
				Object[] ackParams = JdbcUtils.getParams(getEndpointURI(), this.ackParams, message);
				int nbRows = new QueryRunner().update(con, this.ackStmt, ackParams);
				if (nbRows != 1) {
					logger.warn("Row count for ack should be 1 and not " + nbRows);
				}
			}
            UMOMessageAdapter msgAdapter = this.connector.getMessageAdapter(message);
            UMOMessage umoMessage = new MuleMessage(msgAdapter);
            routeMessage(umoMessage, tx, tx != null || endpoint.isSynchronous());
		} finally {
			if (tx == null) {
				JdbcUtils.close(con);
			}
		}
	}
	
	public List getMessages() throws Exception {
		Connection con = null;
		try {
			con = this.connector.getConnection();
			Object[] readParams = JdbcUtils.getParams(getEndpointURI(), this.readParams, null);
			Object results = new QueryRunner().query(con, this.readStmt, readParams, new MapListHandler());
			return (List) results;
		} finally {
			JdbcUtils.close(con);
		}
	}
	
}
