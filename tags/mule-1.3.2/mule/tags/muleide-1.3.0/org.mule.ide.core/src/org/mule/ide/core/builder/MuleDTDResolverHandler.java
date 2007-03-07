/**
 * 
 */
package org.mule.ide.core.builder;

import java.io.IOException;
import java.net.URL;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MuleDTDResolverHandler extends DefaultHandler {
    private boolean seenRoot = false;

    public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
        if (publicId.equals(MuleConfigBuilder.SYMPHONY_SOFT_DTD_MULE_CONFIGURATION_XML_V1_0_EN) ||
            systemId.startsWith(MuleConfigBuilder.HTTP_WWW_SYMPHONYSOFT_COM_DTDS_MULE) || 
            publicId.equals(MuleConfigBuilder.MULESOURCE_DTD_MULE_CONFIGURATION_XML_V1_0_EN) ||
            systemId.startsWith(MuleConfigBuilder.HTTP_MULESOURCE_COM_DTDS_MULE)) {

        	URL dtdURL = null;
        	
        	if (systemId.startsWith(MuleConfigBuilder.HTTP_WWW_SYMPHONYSOFT_COM_DTDS_MULE)) {
        		dtdURL = MuleConfigBuilder.findResourceURL("dtd/" + systemId.substring(MuleConfigBuilder.HTTP_WWW_SYMPHONYSOFT_COM_DTDS_MULE.length()));
        	} else if (systemId.startsWith(MuleConfigBuilder.HTTP_MULESOURCE_COM_DTDS_MULE)) {
        		dtdURL = MuleConfigBuilder.findResourceURL("dtd/" + systemId.substring(MuleConfigBuilder.HTTP_MULESOURCE_COM_DTDS_MULE.length()));
        	}
            try {
                if (dtdURL != null) {
                    return new InputSource(dtdURL.openStream());
                }
            } catch (IOException ioex) {
                throw new SAXException(ioex);
            }
        }
        throw new WrongRootException(systemId + " will not be loaded");
    }

    /* (non-Javadoc)
     * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (seenRoot) {
            super.startElement(uri, localName, qName, attributes);
        } else {
            // Will, this must be the root, then
            if (! qName.equals("mule-configuration"))
                throw new WrongRootException("Only Mule Configurations are checked here, root-element '"+qName + "' not supported");

            seenRoot = true;
        }
    }
}