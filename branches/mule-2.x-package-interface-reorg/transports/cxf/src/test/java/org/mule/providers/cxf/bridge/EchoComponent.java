
package org.mule.providers.cxf.bridge;

import org.mule.api.UMOEventContext;
import org.mule.api.UMOMessage;
import org.mule.api.lifecycle.Callable;

import javax.xml.transform.Source;

public class EchoComponent implements Callable
{

    public Object onCall(UMOEventContext eventContext) throws Exception
    {
        UMOMessage message = eventContext.getMessage();
        Source s = (Source) message.getPayload();
        return s;
    }

}
