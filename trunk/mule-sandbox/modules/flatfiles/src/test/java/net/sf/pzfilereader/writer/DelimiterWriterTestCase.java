/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package net.sf.pzfilereader.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import junit.framework.Assert;

public class DelimiterWriterTestCase extends PZWriterTestCase
{
    public void testWriteCsvNoMappingFile() throws Exception
    {
        OutputStream out = new ByteArrayOutputStream();
        
        DelimiterWriterFactory factory = new DelimiterWriterFactory(';', '"');
        factory.addColumnTitle("FIRSTNAME");
        factory.addColumnTitle("LASTNAME");
        factory.addColumnTitle("ADDRESS");
        factory.addColumnTitle("CITY");
        factory.addColumnTitle("STATE");
        factory.addColumnTitle("ZIP");
        
        PZWriter writer = factory.createWriter(out);
        // write one line of data ... not in the correct order of fields
        writer.addRecordEntry("LASTNAME", "ANAME");
        writer.addRecordEntry("FIRSTNAME", "JOHN");
        writer.addRecordEntry("ZIP", "44035");
        writer.addRecordEntry("CITY", "ELYRIA");
        writer.addRecordEntry("STATE", "OH");
        writer.addRecordEntry("ADDRESS", "1234 CIRCLE CT");
        writer.nextRecord();
        writer.flush();        
        
        // make sure the tests work on Windows and on Linux
        String expected = this.joinLines("FIRSTNAME;LASTNAME;ADDRESS;CITY;STATE;ZIP",
            "JOHN;ANAME;1234 CIRCLE CT;ELYRIA;OH;44035");

        Assert.assertEquals(expected, out.toString());
    }
    
    public void testWriteCsvWithMappingFile() throws Exception
    {
        InputStream mapping = this.getClass().getClassLoader().getResourceAsStream("DelimitedWithHeader.pzmap.xml");
        OutputStream out = new ByteArrayOutputStream();
        
        PZWriter writer = new DelimiterWriterFactory(mapping, ';', '"').createWriter(out);
        writer.addRecordEntry("LASTNAME", "ANAME");
        writer.addRecordEntry("FIRSTNAME", "JOHN");
        writer.addRecordEntry("ZIP", "44035");
        writer.addRecordEntry("CITY", "ELYRIA");
        writer.addRecordEntry("STATE", "OH");
        writer.addRecordEntry("ADDRESS", "1234 CIRCLE CT");
        writer.nextRecord();
        writer.flush();

        String expected = this.joinLines("FIRSTNAME;LASTNAME;ADDRESS;CITY;STATE;ZIP",
            "JOHN;ANAME;1234 CIRCLE CT;ELYRIA;OH;44035");

        Assert.assertEquals(expected, out.toString());
    }
    
    public void testWriteCsvWithMissingColumns() throws Exception
    {
        InputStream mapping = this.getClass().getClassLoader().getResourceAsStream("DelimitedWithHeader.pzmap.xml");
        OutputStream out = new ByteArrayOutputStream();
        
        PZWriter writer = new DelimiterWriterFactory(mapping, ';', '"').createWriter(out);
        // note that we do not provide values for FIRSTNAME and ADDRESS
        writer.addRecordEntry("LASTNAME", "ANAME");
        writer.addRecordEntry("ZIP", "44035");
        writer.addRecordEntry("CITY", "ELYRIA");
        writer.addRecordEntry("STATE", "OH");
        writer.nextRecord();
        writer.flush();

        String expected = this.joinLines("FIRSTNAME;LASTNAME;ADDRESS;CITY;STATE;ZIP",
            ";ANAME;;ELYRIA;OH;44035");

        Assert.assertEquals(expected, out.toString());
    }

    public void testCreateWriterWithNullOutputStream() throws IOException
    {
        try
        {
            new DelimiterWriterFactory((Map)null).createWriter(null);
        }
        catch (NullPointerException npe)
        {
            // this one was expected
        }
    }
    
//    public void testWriterWithoutHeadersAndWithoutMapping()
//    {
//        // TODO this must throw exception
//        Assert.fail();
//    }
    
//    public void testWriteCsvWithMissingColumns()
//    {
//        // TODO
//    }
}
