/**
* Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
*
* The software in this package is published under the terms of the CPAL v1.0
* license, a copy of which has been included with this distribution in the
* LICENSE.txt file.
*/

package org.mule.providers.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DerbyStoredProc
{
    public static void plus(int a, int[] b, double[] c, String[] str)
    {
/*
// You can use any SQL statements in this method. Example:
        try
        {
            Connection conn = DriverManager.getConnection("jdbc:default:connection");
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM TEST");
            ResultSet rset = statement.executeQuery();
            //....
            rset.close();
            statement.close();
        }
        catch (SQLException ex)
        {

        }
*/
        c[0] = a + b[0] + 0.3;
        b[0] = 10;
        str[0] = "test";
    }
}
