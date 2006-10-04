package $package;

import org.mule.transformers.AbstractTransformer;
import org.mule.umo.transformer.TransformerException;

/**
 * Simply insert a white space (blank) character within the String
 * 
 */
public class InsertBlankTransformer extends AbstractTransformer
{
    /**
     * Serial version
     */
    private static final long serialVersionUID = 3570071588127187652L;

    public InsertBlankTransformer()
    {
        registerSourceType(String.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.mule.transformers.AbstractTransformer#doTransform(java.lang.Object)
     */
    public Object doTransform(Object src, String encoding) throws TransformerException
    {
        String source = (String) src;
        StringBuffer withBlanks = new StringBuffer();
        for (int i = 0; i < source.length(); i++)
        {
            if (source.charAt(i) != ' ')
            {
                withBlanks.append(source.charAt(i));
                withBlanks.append(' ');
            }
        }
     
        return withBlanks.toString();
    }

}
