package org.mule.transport.cxf.testmodels;

import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.jws.WebService;
import javax.xml.ws.Holder;

import org.apache.cxf.mime.TestMtom;

@WebService(serviceName = "TestMtomService", 
        portName = "TestMtomPort", 
        targetNamespace = "http://cxf.apache.org/mime", 
        endpointInterface = "org.apache.cxf.mime.TestMtom",
        wsdlLocation = "testutils/mtom_xop.wsdl")
        
public class TestMtomImpl implements TestMtom {

    public void testXop(Holder<String> name, Holder<DataHandler> attachinfo) {
        // TODO Auto-generated method stub
        name.value = "return detail + " + name.value;
        
        try
        {
            InputStream inputStream = attachinfo.value.getInputStream();
            while (inputStream.read() != -1);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        attachinfo.value = new DataHandler(new FileDataSource("src/test/resources/mtom-conf.xml"));
    }

}