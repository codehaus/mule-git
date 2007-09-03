
package net.sf.flatpack.writer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.flatpack.InitialisationException;
import net.sf.flatpack.util.FPConstants;
import net.sf.flatpack.xml.MapParser;

import org.jdom.JDOMException;

public abstract class AbstractWriterFactory extends Object implements WriterFactory
{
    private Map mapping;

    protected AbstractWriterFactory()
    {
        super();

        mapping = new HashMap();
        mapping.put(FPConstants.DETAIL_ID, new ArrayList());
        mapping.put(FPConstants.COL_IDX, new HashMap());
    }

    protected AbstractWriterFactory(Map mapping)
    {
        super();
        this.mapping = mapping;
    }

    protected AbstractWriterFactory(Reader mappingSrc) throws IOException
    {
        this();

        try
        {
            mapping = MapParser.parse(mappingSrc, null);
        }
        catch (JDOMException jde)
        {
            throw new InitialisationException(jde);
        }
    }

    protected Map getColumnMapping()
    {
        // TODO DO: return deep mutable clone here or better: make the Map a first class
        // citizen of the library
        return Collections.unmodifiableMap(mapping);
    }

    public abstract Writer createWriter(java.io.Writer out) throws IOException;
}
