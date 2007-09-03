
package net.sf.flatpack.writer;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.jdom.JDOMException;

public class FixedWriterFactory extends AbstractWriterFactory
{
    public static final char DEFAULT_PADDING_CHARACTER = ' ';

    private final char pad;

    public FixedWriterFactory(Map mapping)
    {
        super(mapping);
        this.pad = DEFAULT_PADDING_CHARACTER;
    }

    public FixedWriterFactory(Reader mappingSrc) throws IOException, JDOMException
    {
        this(mappingSrc, DEFAULT_PADDING_CHARACTER);
    }

    public FixedWriterFactory(Reader mappingSrc, char fillChar) throws IOException
    {
        super(mappingSrc);
        this.pad = fillChar;
    }

    public Writer createWriter(java.io.Writer output) throws IOException
    {
        return new FixedLengthWriter(this.getColumnMapping(), output, pad);
    }
}
