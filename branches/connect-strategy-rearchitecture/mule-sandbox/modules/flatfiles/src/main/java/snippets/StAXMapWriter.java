
package snippets;

import java.io.OutputStream;
import java.util.ArrayList;
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
    private String datasetIdentifier = "dataset";

    // the default tag for a single "record"
    private String recordIdentifier = "record";

    // the default tag for a single "element"
    private String elementIdentifier = "element";

    // XMLOutputFactory should be kept around
    private final XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

    public StAXMapWriter()
    {
        super();
    }

    // identifier for an entire set of records
    public String getDatasetIdentifier()
    {
        return datasetIdentifier;
    }

    // this configures the dataset identifier
    public void setDatasetIdentifier(String identifier)
    {
        datasetIdentifier = identifier;
    }

    // identifier for each new "record"
    public String getRecordIdentifier()
    {
        return recordIdentifier;
    }

    // this configures the record identifier
    public void setRecordIdentifier(String identifier)
    {
        recordIdentifier = identifier;
    }

    // overridable method to retrieve the XML tag for a Map key
    // we also normalize from non-String keys (very simplistic)
    public String getElementIdentifier()
    {
        return elementIdentifier;
    }

    public void setElementIdentifiers(String identifier)
    {
        this.elementIdentifier = identifier;
    }

    public void writeMaps(List maps, OutputStream out) throws XMLStreamException
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

            // write the "dataset" root element
            streamWriter.writeStartElement(this.getDatasetIdentifier());

            // write all "records"
            for (Iterator i = maps.iterator(); i.hasNext();)
            {
                this.writeMap((Map) i.next(), streamWriter);
            }

            // close the dataset
            streamWriter.writeEndElement();

            // close the document
            streamWriter.writeEndDocument();
        }
        catch (XMLStreamException xmlstex)
        {
            // rollback output?
            throw xmlstex;
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
                    throw xmlstex;
                }
            }
        }

    }

    public void writeMap(Map record, XMLStreamWriter streamWriter) throws XMLStreamException
    {
        // begin a single "record"
        streamWriter.writeStartElement(this.getRecordIdentifier());

        // now write each key/value pair
        for (Iterator i = record.entrySet().iterator(); i.hasNext();)
        {
            this.writeMapElement((Map.Entry) i.next(), streamWriter);
        }

        // close the record element
        streamWriter.writeEndElement();
    }

    public void writeMapElement(Map.Entry element, XMLStreamWriter streamWriter) throws XMLStreamException
    {
        streamWriter.writeStartElement(getElementIdentifier());
        streamWriter.writeAttribute("name", element.getKey().toString());
        streamWriter.writeCharacters(element.getValue().toString());
        streamWriter.writeEndElement();
    }

    public static void main(String[] args)
    {
        // our writer
        StAXMapWriter writer = new StAXMapWriter();

        List maps = new ArrayList();

        // create a map with data for a single "record"
        Map data1 = new HashMap();
        data1.put("foo", "Hanz");
        data1.put("bar", "bar");
        data1.put("baz", "baz");
        maps.add(data1);

        // write data to output stream
        try
        {
            writer.writeMaps(maps, System.out);
            System.out.println();
        }
        catch (XMLStreamException sex)
        {
            // handle it
            sex.printStackTrace();
        }
    }

}
