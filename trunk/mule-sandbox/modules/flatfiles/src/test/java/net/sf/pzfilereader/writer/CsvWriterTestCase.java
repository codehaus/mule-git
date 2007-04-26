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

import junit.framework.Assert;
import junit.framework.TestCase;

public class CsvWriterTestCase extends TestCase
{
    public void testWriteCsvNoMappingFile() throws Exception
    {
        OutputStream out = new ByteArrayOutputStream();
        
        CsvWriter writer = PzWriterFactory.newCsvWriter(out, ';', '"');
        // the first line defines the column titles
        writer.addColumnTitle("FIRSTNAME");
        writer.addColumnTitle("LASTNAME");
        writer.addColumnTitle("ADDRESS");
        writer.addColumnTitle("CITY");
        writer.addColumnTitle("STATE");
        writer.addColumnTitle("ZIP");
        writer.nextRecord();
        
        // write one line of data ... not in the correct order of fields
        writer.addRecordEntry("LASTNAME", "ANAME");
        writer.addRecordEntry("FIRSTNAME", "JOHN");
        writer.addRecordEntry("ZIP", "44035");
        writer.addRecordEntry("CITY", "ELYRIA");
        writer.addRecordEntry("STATE", "OH");
        writer.addRecordEntry("ADDRESS","1234 CIRCLE CT");
        writer.nextRecord();
        writer.flush();        
        
        String expected = "FIRSTNAME;LASTNAME;ADDRESS;CITY;STATE;ZIP\n" 
            + "JOHN;ANAME;1234 CIRCLE CT;ELYRIA;OH;44035\n";

        Assert.assertEquals(expected, out.toString());
    }
    
    public void testWriteCsvWithMappingFile() throws Exception
    {
        InputStream mapping = this.getClass().getClassLoader().getResourceAsStream("DelimitedWithHeader.pzmap.xml");
        OutputStream out = new ByteArrayOutputStream();
        
        CsvWriter writer = PzWriterFactory.newCsvWriter(mapping, out, ';', '"');
        writer.addRecordEntry("LASTNAME", "ANAME");
        writer.addRecordEntry("FIRSTNAME", "JOHN");
        writer.addRecordEntry("ZIP", "44035");
        writer.addRecordEntry("CITY", "ELYRIA");
        writer.addRecordEntry("STATE", "OH");
        writer.addRecordEntry("ADDRESS","1234 CIRCLE CT");
        writer.nextRecord();
        writer.flush();

        String expected = "FIRSTNAME;LASTNAME;ADDRESS;CITY;STATE;ZIP\n" 
            + "JOHN;ANAME;1234 CIRCLE CT;ELYRIA;OH;44035\n";

        Assert.assertEquals(expected, out.toString());
    }
    
    public void testCreateWriterWithNullOutputStream() throws IOException
    {
        try
        {
            new DefaultCsvWriter(null, ';', '"');
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
