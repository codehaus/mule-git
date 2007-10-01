/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.test.integration.transaction;

import org.mule.providers.jdbc.JdbcUtils;
import org.mule.tck.FunctionalTestCase;
import org.mule.util.MuleDerbyTestUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

public class XABridgeJmsJdbcTestCase extends FunctionalTestCase
{
    private static String connectionString;
    
    protected String getConfigResources()
    {
        return "org/mule/test/integration/transaction/xabridge-jms-jdbc-mule.xml";
    }
    
    protected void suitePreSetUp() throws Exception
    {
        String dbName = MuleDerbyTestUtils.loadDatabaseName("derby.properties", "database.name");

        MuleDerbyTestUtils.defaultDerbyCleanAndInit("derby.properties", "database.name");
        connectionString = "jdbc:derby:" + dbName;

        super.suitePreSetUp();
    }

    protected void doPostFunctionalSetUp() throws Exception
    {
        emptyTable();
    }

    protected void emptyTable() throws Exception
    {
        try
        {
            execSqlUpdate("DELETE FROM TEST");
        }
        catch (Exception e)
        {
            execSqlUpdate("CREATE TABLE TEST(ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 0)  NOT NULL PRIMARY KEY,TYPE INTEGER,DATA VARCHAR(255),ACK TIMESTAMP,RESULT VARCHAR(255))");
        }
    }

    protected Connection getConnection() throws Exception
    {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        return DriverManager.getConnection(connectionString);
    }

    protected List execSqlQuery(String sql) throws Exception
    {
        Connection con = null;
        try
        {
            con = getConnection();
            return (List)new QueryRunner().query(con, sql, new ArrayListHandler());
        }
        finally
        {
            JdbcUtils.close(con);
        }
    }

    protected int execSqlUpdate(String sql) throws Exception
    {
        Connection con = null;
        try
        {
            con = getConnection();
            return new QueryRunner().update(con, sql);
        }
        finally
        {
            JdbcUtils.close(con);
        }
    }

    protected void doTestXaBridge(boolean rollback) throws Exception
    {
        XABridgeComponent.mayRollback = rollback;

        List results = execSqlQuery("SELECT * FROM TEST");
        assertEquals(0, results.size());

        for (int i = 0; i < 10; i++)
        {
            execSqlUpdate("INSERT INTO TEST(TYPE, DATA) VALUES (1, 'Test " + i + "')");
        }
        results = execSqlQuery("SELECT * FROM TEST WHERE TYPE = 1");
        assertEquals(10, results.size());

        long t0 = System.currentTimeMillis();
        while (true)
        {
            results = execSqlQuery("SELECT * FROM TEST WHERE TYPE = 2");
            logger.info("Results found: " + results.size());
            if (results.size() >= 10)
            {
                break;
            }
            assertTrue(System.currentTimeMillis() - t0 < 20000);
            Thread.sleep(500);
        }
    }

    public void testXaBridgeWithoutRollbacks() throws Exception
    {
        doTestXaBridge(false);
    }

    public void testXaBridgeWithRollbacks() throws Exception
    {
        doTestXaBridge(true);
    }
}
