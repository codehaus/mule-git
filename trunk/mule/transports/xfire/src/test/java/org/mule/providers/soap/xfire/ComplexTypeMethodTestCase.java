package org.mule.providers.soap.xfire;

import org.mule.extras.client.MuleClient;
import org.mule.impl.MuleMessage;
import org.mule.tck.FunctionalTestCase;
import org.mule.tck.testmodels.services.Person;
import org.mule.tck.testmodels.services.PersonResponse;
import org.mule.umo.UMOMessage;

public class ComplexTypeMethodTestCase extends FunctionalTestCase
{
    public void testSendComplexType() throws Exception
    {
        MuleClient client = new MuleClient();
        UMOMessage result = client.send("xfireEndpoint", new MuleMessage(new Person("Jane", "Doe")));
        assertNotNull(result.getPayload());
        assertTrue(result.getPayload() instanceof PersonResponse);
        assertTrue(((PersonResponse)result.getPayload()).getPerson().getFirstName().equalsIgnoreCase("Jane"));
        // call this just to be sure it doesn't throw an exception
        ((PersonResponse)result.getPayload()).getTime();
    }

    public void testSendComplexTypeUsingWSDLXfire() throws Exception
    {
        MuleClient client = new MuleClient();
        UMOMessage result = client.send("wsdlEndpoint", new MuleMessage(new Person("Jane", "Doe")));
        assertNotNull(result.getPayload());
        assertTrue(result.getPayload() instanceof PersonResponse);
        assertTrue(((PersonResponse)result.getPayload()).getPerson().getFirstName().equalsIgnoreCase("Jane"));
        // call this just to be sure it doesn't throw an exception
        ((PersonResponse)result.getPayload()).getTime();
    }

    protected String getConfigResources() 
    {
        return "xfire-complex-type-conf.xml";
    }
}