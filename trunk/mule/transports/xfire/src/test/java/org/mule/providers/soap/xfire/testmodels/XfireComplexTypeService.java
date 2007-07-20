package org.mule.providers.soap.xfire.testmodels;

import org.mule.tck.testmodels.services.Person;
import org.mule.tck.testmodels.services.PersonResponse;

public class XfireComplexTypeService 
{
    public PersonResponse addPersonWithConfirmation(Person person) 
    {
        return new PersonResponse(person);
    }
}
