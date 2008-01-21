package org.mule.providers.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DerbyStoredProc {

    public static void plus(int a, int[] b, double[] c, String[] str) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:default:connection");
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM TEST");
            ResultSet rset = statement.executeQuery();
        } catch (SQLException ex) {

        }
        c[0] = a + b[0] + 0.3;
        b[0] = 10;
        str[0] = "test";
        //return result;
    }
}
