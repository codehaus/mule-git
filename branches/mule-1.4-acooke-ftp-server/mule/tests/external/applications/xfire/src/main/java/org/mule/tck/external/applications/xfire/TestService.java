package org.mule.tck.external.applications.xfire;

import org.codehaus.xfire.fault.FaultInfoException;

public interface TestService
{
    public Test[] getTests();
    public Test getTest(String key) throws FaultInfoException;
}
