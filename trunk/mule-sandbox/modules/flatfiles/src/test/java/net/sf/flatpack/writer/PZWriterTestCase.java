package net.sf.flatpack.writer;

import junit.framework.TestCase;

public abstract class PZWriterTestCase extends TestCase
{
    private String lineSeparator = System.getProperty("line.separator");

    protected String joinLines(String line1, String line2)
    {
        if (line1 == null)
        {
            throw new IllegalArgumentException("parameter string1 may not be null");
        }
        
        StringBuffer result = new StringBuffer(line1);
        result.append(lineSeparator);
        if (line2 != null)
        {
            result.append(line2);
            result.append(lineSeparator);
        }
        
        return result.toString();
    }
    
    protected String normalizeLineEnding(String line)
    {
        return this.joinLines(line, null);
    }
}


