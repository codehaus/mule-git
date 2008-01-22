package org.mule.providers.cxf.jaxws;

import org.mule.api.UMOEventContext;

public class ClientMessageGenerator implements org.mule.api.lifecycle.Callable 
{
    
    public Object onCall(UMOEventContext eventContext) throws Exception 
    {
        return generate();
    }

    public String generate() 
    {
        return "Dan";
    }
}
