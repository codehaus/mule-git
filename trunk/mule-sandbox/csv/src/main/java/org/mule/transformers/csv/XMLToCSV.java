
package org.mule.transformers.csv;

import java.io.StringWriter;
import java.io.Writer;

import org.mule.transformers.xml.AbstractXStreamTransformer;
import org.mule.umo.UMOEventContext;
import org.mule.umo.transformer.TransformerException;

/**
 * @author WestelinckK Transform an XML string to its CSV representation.
 */
public class XMLToCSV extends AbstractXStreamTransformer
{
    private static final long serialVersionUID = -1563436217459907518L;
    private char separator;

    public XMLToCSV()
    {
        super.registerSourceType(String.class);
    }

    // @Override
    public Object transform(Object src, String encoding, UMOEventContext context) throws TransformerException
    {
        try
        {
            if (src instanceof String)
            {
                Writer stringWriter = new StringWriter();
                CSVWriter writer = new CSVWriterImpl(stringWriter, separator);
                Object o = getXStream().fromXML((String)src);
                writer.write(o);
                return stringWriter.toString();
            }
        }
        catch (Exception e)
        {
            throw new TransformerException(this, e);
        }
        return null;
    }

    public char getSeparator()
    {
        return separator;
    }

    public void setSeparator(char separator)
    {
        this.separator = separator;
    }

}
