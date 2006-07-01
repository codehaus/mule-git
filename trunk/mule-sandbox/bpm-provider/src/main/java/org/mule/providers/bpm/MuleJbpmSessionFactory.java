package org.mule.providers.bpm;

import org.jbpm.JbpmConfiguration;
import org.jbpm.db.JbpmSessionFactory;

public class MuleJbpmSessionFactory extends JbpmSessionFactory {

    // The event router is used to generate Mule events from an executing process.
    private EventRouter eventRouter;

    public MuleJbpmSessionFactory() {
        super(createConfiguration(JbpmConfiguration.getString("jbpm.hibernate.cfg.xml")));
    }

    public EventRouter getEventRouter() {
        return eventRouter;
    }

    public void setEventRouter(EventRouter eventRouter) {
        this.eventRouter = eventRouter;
    }
}
