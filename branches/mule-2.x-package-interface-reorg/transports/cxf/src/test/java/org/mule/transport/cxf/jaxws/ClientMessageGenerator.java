package org.mule.transport.cxf.jaxws;

import org.mule.api.EventContext;

public class ClientMessageGenerator implements org.mule.api.lifecycle.Callable 
{
    
    public Object onCall(EventContext eventContext) throws Exception 
    {
        return generate();
    }

    public String generate() 
    {
        return "Dan";
    }
}
