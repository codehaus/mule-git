package net.sf.pzfilereader.writer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import junit.framework.Assert;

import net.sf.flatpack.InitialisationException;
import net.sf.flatpack.writer.FixedWriterFactory;
import net.sf.flatpack.writer.Writer;

public class FixedLengthWriterTestCase extends PZWriterTestCase
{
    public void testWriteFixedLength() throws Exception
    {
        StringWriter out = new StringWriter();
        Writer writer = new FixedWriterFactory(this.getMapping()).createWriter(out);

        writer.addRecordEntry("LASTNAME", "DOE");
        writer.addRecordEntry("ADDRESS", "1234 CIRCLE CT");
        writer.addRecordEntry("STATE", "OH");
        writer.addRecordEntry("ZIP", "44035");
        writer.addRecordEntry("FIRSTNAME", "JOHN");
        writer.addRecordEntry("CITY", "ELYRIA");
        writer.nextRecord();
        writer.flush();
        
        String expected = this.normalizeLineEnding("JOHN                               DOE                                1234 CIRCLE CT                                                                                      ELYRIA                                                                                              OH44035");
        Assert.assertEquals(expected, out.toString());
    }
    
    public void testWriterWithDifferentFillChar() throws Exception
    {
        StringWriter out = new StringWriter();
        Writer writer = new FixedWriterFactory(this.getMapping(), '.').createWriter(out);

        writer.addRecordEntry("LASTNAME", "DOE");
        writer.addRecordEntry("ADDRESS", "1234 CIRCLE CT");
        writer.addRecordEntry("STATE", "OH");
        writer.addRecordEntry("ZIP", "44035");
        writer.addRecordEntry("FIRSTNAME", "JOHN");
        writer.addRecordEntry("CITY", "ELYRIA");
        writer.nextRecord();
        writer.flush();
        
        String expected = this.normalizeLineEnding("JOHN...............................DOE................................1234 CIRCLE CT......................................................................................ELYRIA..............................................................................................OH44035");
        Assert.assertEquals(expected, out.toString());
    }

    public void testCreateParserWithMalformedMappingFile() throws Exception
    {
        try
        {
            InputStream mapping = this.getClass().getClassLoader().getResourceAsStream("BrokenMapping.pzmap.xml");
            InputStreamReader mappingReader = new InputStreamReader(mapping);
            new FixedWriterFactory(mappingReader);
            Assert.fail();
        }
        catch (InitialisationException ie)
        {
            // this excecption must occur, mapping xml is invalid
        }
    }
    
    public void testWriteStringWiderThanColumnDefinition() throws Exception
    {
        StringWriter out = new StringWriter();        
        Writer writer = new FixedWriterFactory(this.getMapping()).createWriter(out);
        try
        {
            writer.addRecordEntry("STATE", "THISISTOOLONG");
            Assert.fail("writing entries that are too long should fail");
        }
        catch (IllegalArgumentException iae)
        {
            // expected exception
        }
    }
    
    public void testWriteNullColumn() throws Exception
    {
        StringWriter out = new StringWriter();
        Writer writer = new FixedWriterFactory(this.getMapping()).createWriter(out);

        writer.addRecordEntry("LASTNAME", "DOE");
        writer.addRecordEntry("ADDRESS", "1234 CIRCLE CT");
        writer.addRecordEntry("STATE", "OH");
        writer.addRecordEntry("ZIP", "44035");
        // note that we don't write a firstname
        writer.addRecordEntry("FIRSTNAME", null);
        writer.addRecordEntry("CITY", "ELYRIA");
        writer.nextRecord();
        writer.flush();
        
        String expected = this.normalizeLineEnding("                                   DOE                                1234 CIRCLE CT                                                                                      ELYRIA                                                                                              OH44035");
        Assert.assertEquals(expected, out.toString());
    }
    
    private Reader getMapping()
    {
        InputStream mapping = this.getClass().getClassLoader().getResourceAsStream("FixedLength.pzmap.xml");
        return new InputStreamReader(mapping);
    }
}


