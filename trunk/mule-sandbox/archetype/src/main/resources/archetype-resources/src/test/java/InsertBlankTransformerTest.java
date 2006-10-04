package $package;

import org.mule.umo.transformer.TransformerException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Mule's embedded app.
 */
public class InsertBlankTransformerTest extends TestCase
{
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( InsertBlankTransformerTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testTrasformer()
    {
        InsertBlankTransformer trasf = new InsertBlankTransformer();
        String source = "CHANGE ME!";
        String result = "C H A N G E M E ! ";
        try
        {
            String checkMe = (String) trasf.doTransform(source, "ISO-8859-1");
            assertEquals(checkMe, result);
        }
        catch (TransformerException tfe)
        {
            fail(tfe.getDetailedMessage());
        }
    }
}
