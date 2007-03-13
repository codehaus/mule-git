package org.mule.providers.soap.xfire;

import javax.xml.parsers.DocumentBuilderFactory;

import org.codehaus.xfire.DefaultXFire;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.server.http.XFireHttpServer;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.ServiceFactory;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.service.invoker.ObjectInvoker;
import org.codehaus.xfire.soap.SoapConstants;
import org.mule.extras.client.MuleClient;
import org.mule.impl.MuleEvent;
import org.mule.impl.MuleMessage;
import org.mule.impl.MuleSession;
import org.mule.impl.endpoint.MuleEndpoint;
import org.mule.providers.AbstractConnector;
import org.mule.tck.AbstractMuleTestCase;
import org.mule.umo.UMOMessage;
import org.mule.umo.UMOSession;
import org.mule.umo.endpoint.UMOEndpoint;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author bussieto
 *
 */
public class XFireWsdlTestCase extends AbstractMuleTestCase {
    private Service service;
    private XFire xfire;
    private ServiceFactory factory;
    private XFireHttpServer server;
    

    
	public void doSetUp() throws Exception
    {
        if (xfire == null) {
        	xfire = new DefaultXFire();
        	// create a service
            service = getServiceFactory().create(ServiceTest.class);
            service.setProperty(ObjectInvoker.SERVICE_IMPL_CLASS, ServiceTestImpl.class);
            xfire.getServiceRegistry().register(service);
            // start a WWW server to listen on
            server = new XFireHttpServer(xfire);
            server.setPort(8391);
        }
        server.start();
    	
    }
	
	

	protected void doTearDown() throws Exception {
		server.stop();
	}



	public void testOriginalMethod()
    throws Exception
    {
		// create a payload
    	Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    	Element root = d.createElementNS(null,"echo");
    	d.appendChild(root);
    	root.appendChild(d.createTextNode("value"));

    	// create a MuleClient
    	MuleClient mc = new MuleClient(true);

    	// send the payload to the local server (with the ?wsdl parameter)
    	UMOMessage message = mc.send("wsdl-xfire:http://127.0.0.1:8391/ServiceTest?wsdl&method=echo", new MuleMessage(d));
    	
    	// check the result
    	Object o = message.getPayload();
    	assertNotNull(o);
    	assertTrue(o instanceof Document);
    	Document response = (Document)o;
    	Node n = response.getDocumentElement().getFirstChild();
    	assertTrue(n.getFirstChild().getNodeValue().equals("value"));
    	
    }
    
    public void testWsdlUrlMethod() 
    throws Exception
    {
    	// create a request
    	Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    	Element root = d.createElementNS(null,"echo");
    	d.appendChild(root);
    	root.appendChild(d.createTextNode("value"));

    	// simulate the creation of the endpoint
		UMOEndpoint endpoint = MuleEndpoint.getOrCreateEndpointForUri("wsdl-xfire:http://127.0.0.1:8391/ServiceTest?method=echo",UMOEndpoint.ENDPOINT_TYPE_SENDER);
		endpoint.getConnector().initialise(); // force the initialization of the connector (we are in a Test context) 

		// test the new property wsdlUrl we could have used a file:// too 
		endpoint.setProperty("wsdlUrl", "http://127.0.0.1:8391/ServiceTest?wsdl");

		UMOMessage message = new MuleMessage(d);
		UMOSession session = new MuleSession(message,
				((AbstractConnector) endpoint.getConnector())
						.getSessionHandler());    	
		MuleEvent event = new MuleEvent(message, endpoint, session,
				true);
		
		// Send the message on the wire
		UMOMessage responseMessage = session.sendEvent(event);

		// check the payload
		Object o = responseMessage.getPayload();
    	assertNotNull(o);
    	assertTrue(o instanceof Document);
    	Document response = (Document)o;
    	Node n = response.getDocumentElement().getFirstChild();
    	assertTrue(n.getFirstChild().getNodeValue().equals("value"));
    }

    
	private ServiceFactory getServiceFactory() {
		if (factory == null) {
	        ObjectServiceFactory osf = new ObjectServiceFactory(xfire.getTransportManager());
	        osf.setUse(SoapConstants.USE_LITERAL);
	        osf.setStyle(SoapConstants.STYLE_DOCUMENT);
	    	osf.setVoidOneWay(true);
	        osf.setBindingCreationEnabled(true);
	        factory = osf;
		}
		return factory;
	}

}
