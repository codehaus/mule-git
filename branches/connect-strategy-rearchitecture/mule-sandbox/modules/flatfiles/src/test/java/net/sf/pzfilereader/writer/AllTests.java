package net.sf.pzfilereader.writer;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests
{

    public static Test suite()
    {
        TestSuite suite = new TestSuite("Test for net.sf.pzfilereader.writer");
        //$JUnit-BEGIN$
        suite.addTestSuite(FixedLengthWriterTestCase.class);
        suite.addTestSuite(DelimiterWriterTestCase.class);
        //$JUnit-END$
        return suite;
    }

}
