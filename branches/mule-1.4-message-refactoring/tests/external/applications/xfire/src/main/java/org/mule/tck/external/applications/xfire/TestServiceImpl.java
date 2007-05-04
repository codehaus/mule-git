package org.mule.tck.external.applications.xfire;

import org.codehaus.xfire.fault.FaultInfoException;

public class TestServiceImpl implements TestService
{
    private Test[] tests = new Test[] {};

    public TestServiceImpl()
    {
        tests = new Test[] { new Test("test1"), new Test("test2") };
    }

    public Test[] getTests()
    {
        return tests;
    }

    public Test getTest(String key) throws FaultInfoException
    {
        for (int i = 0; i < tests.length; i++)
        {
            if (tests[i].getKey().equals(key)) return tests[i];
        }

        throw new FaultInfoException("No test found with key " + key);
    }

}
