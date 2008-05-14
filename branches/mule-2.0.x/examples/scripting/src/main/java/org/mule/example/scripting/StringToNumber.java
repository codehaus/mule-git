package org.mule.example.scripting;


import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;
import org.mule.util.NumberUtils;

/** 
 * Converts a string to a number.
 */
public class StringToNumber extends AbstractTransformer 
{
    /** Convert the string to an integer (by default it will convert it to a double) */
    private boolean integer = false;
    
    public StringToNumber()
    {
        registerSourceType(String.class);
        setReturnClass(Number.class);
    }

    public Object doTransform(Object src, String encoding) throws TransformerException
    {         
        if (integer)
        {
            return NumberUtils.toInt(src);
        }
        else
        {
            return NumberUtils.toDouble(src);
        }
    }

    public boolean isInteger()
    {
        return integer;
    }

    public void setInteger(boolean integer)
    {
        this.integer = integer;
    }
}
