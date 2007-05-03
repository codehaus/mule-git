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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.pzfilereader.InitialisationException;
import net.sf.pzfilereader.util.PZConstants;
import net.sf.pzfilereader.xml.PZMapParser;

import org.jdom.JDOMException;

public abstract class AbstractWriterFactory extends Object
{
    private Map mapping;

    public AbstractWriterFactory()
    {
        super();
        
        mapping = new HashMap();
        mapping.put(PZConstants.DETAIL_ID, new ArrayList());
        mapping.put(PZConstants.COL_IDX, new HashMap());
    }
    
    public AbstractWriterFactory(Map mapping)
    {
        super();
        this.mapping = mapping;
    }
    
    public AbstractWriterFactory(InputStream mappingSrc) throws IOException
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
        return Collections.unmodifiableMap(mapping);
    }
}


