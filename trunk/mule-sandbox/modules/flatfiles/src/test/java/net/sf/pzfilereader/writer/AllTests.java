
package net.sf.pzfilereader.writer;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{

    public static Test suite()
    {
        TestSuite suite = new TestSuite("Test for net.sf.pzfilereader.writer");
        //$JUnit-BEGIN$
        suite.addTestSuite(DelimiterWriterTestCase.class);
        suite.addTestSuite(FixedLengthWriterTestCase.class);
        //$JUnit-END$
        return suite;
    }

}
