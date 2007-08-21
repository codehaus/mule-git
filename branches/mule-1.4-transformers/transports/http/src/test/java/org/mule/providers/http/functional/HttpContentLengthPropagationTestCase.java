package org.mule.providers.http.functional;

import org.mule.extras.client.MuleClient;
import org.mule.impl.MuleMessage;
import org.mule.tck.FunctionalTestCase;
import org.mule.transformers.xml.XsltTransformer;
import org.mule.umo.UMOMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class HttpContentLengthPropagationTestCase extends FunctionalTestCase
{
	private static final String payload_path = "target/test-classes/test-xml-payload.xml";
	private static final String stylesheet_path = "target/test-classes/stylesheet.xsl";
	private byte[] fileContents;
	
	public HttpContentLengthPropagationTestCase()
	{
	    super();
	    this.setDisposeManagerPerSuite(true);
	}

	protected String getConfigResources() 
	{
		return "http-content-length-propagation-conf.xml";
	}
	
	private void readFile(String path) throws IOException
	{
	    FileInputStream stream = new FileInputStream(path);
        File file = new File(path);
        fileContents = new byte[(int)file.length()];
        int bytesRead;
        int totalbytesRead = 0;
        
        while(totalbytesRead < file.length())
        {
            bytesRead = stream.read(fileContents, totalbytesRead, (int)file.length());
            totalbytesRead += bytesRead;
        }
	}
	
	public byte[] localTransform() throws Exception
	{
	    XsltTransformer trans = new XsltTransformer();
	    trans.setXslFile(stylesheet_path);

        return (byte[])trans.doTransform(fileContents, "UTF-8");
	}
	
	public void testContentLengthPropagation() throws Exception
	{
		readFile(payload_path);
		
		MuleClient client = new MuleClient();
		UMOMessage result = client.send("http://localhost:8085", new MuleMessage(fileContents));
		
		assertEquals(new String(localTransform()), result.getPayloadAsString());

	}

}
