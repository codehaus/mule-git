package net.sf.pzfilereader.writer;

import java.io.IOException;
import java.io.OutputStream;

public interface PZWriterFactory
{
    public PZWriter createWriter(OutputStream out) throws IOException;
}
