
package net.sf.flatpack.writer;

import java.io.IOException;

public interface WriterFactory
{
    public Writer createWriter(java.io.Writer out) throws IOException;
}
