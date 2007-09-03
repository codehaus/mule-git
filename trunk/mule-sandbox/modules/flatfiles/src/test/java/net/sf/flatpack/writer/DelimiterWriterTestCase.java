package net.sf.flatpack.writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Map;

import junit.framework.Assert;

import net.sf.flatpack.writer.DelimiterWriterFactory;
import net.sf.flatpack.writer.Writer;

public class DelimiterWriterTestCase extends PZWriterTestCase
{
    public void testWriteCsvNoMappingFile() throws Exception
    {
        StringWriter out = new StringWriter();
        
        DelimiterWriterFactory factory = new DelimiterWriterFactory(';', '"');
        factory.addColumnTitle("FIRSTNAME");
        factory.addColumnTitle("LASTNAME");
        factory.addColumnTitle("ADDRESS");
        factory.addColumnTitle("CITY");
        factory.addColumnTitle("STATE");
        factory.addColumnTitle("ZIP");
        
        Writer writer = factory.createWriter(out);
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
        Reader mappingReader = new InputStreamReader(mapping);
        StringWriter out = new StringWriter();
        
        Writer writer = new DelimiterWriterFactory(mappingReader, ';', '"').createWriter(out);
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
        InputStreamReader mappingReader = new InputStreamReader(mapping);
        StringWriter out = new StringWriter();
        
        Writer writer = new DelimiterWriterFactory(mappingReader, ';', '"').createWriter(out);
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
    
    public void testCreateWriterWithoutColumnMapping() throws Exception
    {
        try
        {
            Writer writer = new DelimiterWriterFactory(';','"').createWriter(new StringWriter());
            writer.addRecordEntry("ThisColumnDoesNotExist", "foo");
            Assert.fail("Writing to a DelimiterWriter without column mapping is not supported");
        }
        catch (IllegalArgumentException iae)
        {
            // exception was expected
        }
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
    
    public void testWriteValueWithQualifier() throws Exception
    {
        DelimiterWriterFactory factory = new DelimiterWriterFactory(';', '"');
        factory.addColumnTitle("col1");
        factory.addColumnTitle("col2");
        
        StringWriter out = new StringWriter();
        Writer writer = factory.createWriter(out);
        writer.addRecordEntry("col1", "value;with;delimiter");
        writer.addRecordEntry("col2", "normal value");
        writer.nextRecord();
        writer.flush();
        
        String expected = this.joinLines("col1;col2", "\"value;with;delimiter\";normal value");
        Assert.assertEquals(expected, out.toString());
    }
}
