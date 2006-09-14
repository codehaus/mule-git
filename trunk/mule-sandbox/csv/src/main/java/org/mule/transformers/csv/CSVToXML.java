
package org.mule.transformers.csv;

import com.thoughtworks.xstream.XStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import org.mule.transformers.xml.AbstractXStreamTransformer;
import org.mule.umo.UMOEventContext;
import org.mule.umo.transformer.TransformerException;

/**
 * @author WestelinckK Transform a CSV string to its XML representation.
 */
public class CSVToXML extends AbstractXStreamTransformer
{
    private static final long serialVersionUID = -6347945833013744970L;
    private ArrayList fieldNames;
    private char separator;

    public Object transform(Object src, String encoding, UMOEventContext context) throws TransformerException
    {
        try
        {
            XStream xs = getXStream();
            LabeledArrayConvertor convertor = new LabeledArrayConvertor(xs.getClassMapper(), fieldNames);
            xs.registerConverter(convertor);

            CSVReader reader = new CSVReaderImpl(getReader(src), separator);
            Object o = reader.parse();

            return xs.toXML(o);
        }
        catch (Exception e)
        {
            throw new TransformerException(this, e);
        }
    }

    protected Reader getReader(Object src) throws Exception
    {
        if (src instanceof byte[])
        {
            return getReader((byte[])src);
        }
        else if (src instanceof String)
        {
            return getReader((String)src);
        }
        else if (src instanceof File)
        {
            return getReader((File)src);
        }
        else
        {
            return null;
        }
    }

    protected Reader getReader(byte[] src) throws Exception
    {
        return getReader(new String(src));
    }

    protected Reader getReader(String src) throws Exception
    {
        return new BufferedReader(new StringReader(src));
    }

    protected Reader getReader(File src) throws Exception
    {
        return new BufferedReader(new FileReader(src));
    }

    public void setFieldNames(ArrayList fieldNames)
    {
        this.fieldNames = fieldNames;
    }

    public ArrayList getFieldNames()
    {
        return fieldNames;
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
