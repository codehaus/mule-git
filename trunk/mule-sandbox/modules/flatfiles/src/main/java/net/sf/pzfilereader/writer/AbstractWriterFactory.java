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
import java.util.Collections;
import java.util.Map;

import net.sf.pzfilereader.InitialisationException;
import net.sf.pzfilereader.xml.PZMapParser;

import org.jdom.JDOMException;

public abstract class AbstractWriterFactory extends Object
{
    private Map parsedMapping;

    public AbstractWriterFactory()
    {
        super();
        parsedMapping = null;
    }
    
    public AbstractWriterFactory(InputStream mappingSrc) throws IOException
    {
        this();
        
        try
        {
            parsedMapping = PZMapParser.parse(mappingSrc);
        }
        catch (JDOMException jde)
        {
            throw new InitialisationException(jde);
        }
    }
    
    protected Map getParsedMapping()
    {
        return Collections.unmodifiableMap(parsedMapping);
    }
}


