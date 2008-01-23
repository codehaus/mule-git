package org.mule.transport.cxf.jaxws;

import org.mule.api.MuleEventContext;

public class ClientMessageGenerator implements org.mule.api.lifecycle.Callable 
{
    
    public Object onCall(MuleEventContext eventContext) throws Exception 
    {
        return generate();
    }

    public String generate() 
    {
        return "Dan";
    }
}
