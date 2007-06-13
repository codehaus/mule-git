package net.sf.pzfilereader.writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.pzfilereader.InitialisationException;
import net.sf.pzfilereader.util.PZConstants;
import net.sf.pzfilereader.xml.PZMapParser;

import org.jdom.JDOMException;

public abstract class AbstractWriterFactory extends Object implements PZWriterFactory
{
    private Map mapping;

    protected AbstractWriterFactory()
    {
        super();
        
        mapping = new HashMap();
        mapping.put(PZConstants.DETAIL_ID, new ArrayList());
        mapping.put(PZConstants.COL_IDX, new HashMap());
    }
    
    protected AbstractWriterFactory(Map mapping)
    {
        super();
        this.mapping = mapping;
    }
    
    protected AbstractWriterFactory(InputStream mappingSrc) throws IOException
    {
        this();
        
        try
        {
            mapping = PZMapParser.parse(mappingSrc);
        }
        catch (JDOMException jde)
        {
            throw new InitialisationException(jde);
        }
    }
    
    protected Map getColumnMapping()
    {
        // TODO DO: return deep mutable clone here or better: make the Map a first class citizen of the library
        return Collections.unmodifiableMap(mapping);
    }

    public abstract PZWriter createWriter(OutputStream out) throws IOException;
}


