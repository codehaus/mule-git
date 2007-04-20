
package snippets;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class StAXMapWriter
{
    // indicates whether we want to write the XML document header
    private boolean writeDocumentHeader = true;

    // the default XML root element tag
    private String defaultRecordIdentifier = "record";

    // this allows for a simple, customizable key->tag mapping
    private Map/* <?, String> */keyToElementMapping = Collections.EMPTY_MAP;

    // XMLOutputFactory should be kept around
    private final XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

    public StAXMapWriter()
    {
        super();
    }

    // identifier for each new "record"
    public String getDefaultRecordIdentifier()
    {
        return defaultRecordIdentifier;
    }

    // identifier for this particular Map
    public String getRecordIdentifier(Map/* <?, ?> */data)
    {
        return (data.containsKey("mumble") ? "address" : this.getDefaultRecordIdentifier());
    }

    // this configures the default record identifier
    public void setDefaultRecordIdentifier(String identifier)
    {
        defaultRecordIdentifier = identifier;
    }

    // overridable method to retrieve the XML tag for a Map key
    // we also normalize from non-String keys (very simplistic)
    public String getElementIdentifier(Map.Entry/* <?, ?> */entry)
    {
        Object key = entry.getKey();
        String elementIdentifier = (String) keyToElementMapping.get(key);
        return (elementIdentifier != null ? elementIdentifier : key.toString());
    }

    public void setElementIdentifiers(Map/* <?, String> */keyToTagMapping)
    {
        this.keyToElementMapping = keyToTagMapping;
    }

    public String getElementValue(Map.Entry/* <?, ?> */entry)
    {
        return entry.getValue().toString();
    }

    public void writeMapsToXMLStream(List/* <Map<?, ?>> */maps, OutputStream out) throws XMLStreamException
    {
        XMLStreamWriter streamWriter = null;

        try
        {
            // create XML stream writer
            streamWriter = outputFactory.createXMLStreamWriter(out);

            // write XML header only when needed
            if (writeDocumentHeader)
            {
                streamWriter.writeStartDocument();
            }

            for (Iterator/* <Map<?, ?>> */i = maps.iterator(); i.hasNext();)
            {
                this.writeMapToXMLStream((Map) i.next(), streamWriter);
            }

            // close the document
            streamWriter.writeEndDocument();
        }
        catch (XMLStreamException xmlstex)
        {
            // rollback output?
        }
        finally
        {
            // close everything
            if (streamWriter != null)
            {
                try
                {
                    streamWriter.close();
                }
                catch (XMLStreamException xmlstex)
                {
                    // give up :(
                }
            }
        }

    }

    public void writeMapToXMLStream(Map/* <?, ?> */record, XMLStreamWriter streamWriter)
        throws XMLStreamException
    {
        // write the "root element"
        streamWriter.writeStartElement(this.getRecordIdentifier(record));

        // now write each key/value pair
        for (Iterator i/* <Map.Entry<?, ?> */= record.entrySet().iterator(); i.hasNext();)
        {
            Map.Entry/* <?, ?> */e = (Map.Entry) i.next();
            streamWriter.writeStartElement(this.getElementIdentifier(e));
            streamWriter.writeCharacters(this.getElementValue(e));
            streamWriter.writeEndElement();
        }

        // close the record element
        streamWriter.writeEndElement();
    }

    public static void main(String[] args)
    {
        // our writer
        StAXMapWriter writer = new StAXMapWriter();

        // configure key mapping
        Map/* <String, String> */keyMapping = Collections.singletonMap("foo", "hanz");
        writer.setElementIdentifiers(keyMapping);

        List/* <Map<?, ?>> */maps = new ArrayList/* <Map<?, ?>> */();

        // create a map with data for a single "record"
        Map/* <String, String> */data1 = new HashMap/* <String, String> */();
        // remember that the foo key will result in a different element
        data1.put("foo", "Hanz");
        data1.put("bar", "Bar");
        data1.put("baz", "xyzzy");
        data1.put("gargle", "xxxx");
        maps.add(data1);

        Map/* <String, String> */data2 = new HashMap/* <String, String> */(data1);
        // add a key which will result in a different root element
        data2.put("mumble", "yyyy");
        maps.add(data2);

        // write data to output stream
        try
        {
            writer.writeMapsToXMLStream(maps, System.out);
            System.out.println();
        }
        catch (XMLStreamException sex)
        {
            // handle it
            sex.printStackTrace();
        }
    }

}
