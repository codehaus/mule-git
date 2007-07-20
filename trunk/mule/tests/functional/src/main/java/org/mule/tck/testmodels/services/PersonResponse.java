package org.mule.tck.testmodels.services;

public class PersonResponse {
    private Person person;
    private long time;

    public PersonResponse()
    {
        // empty constructor
    }

    public PersonResponse(Person person){
        this.person = person;
        this.time = System.currentTimeMillis();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}