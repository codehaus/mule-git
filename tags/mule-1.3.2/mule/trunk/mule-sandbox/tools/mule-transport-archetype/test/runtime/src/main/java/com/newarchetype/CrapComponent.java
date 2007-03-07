/*
 * CrapComponent.java
 *
 */
package com.newarchetype;

import java.util.logging.Logger;

import javax.jbi.component.Component;
import javax.jbi.component.ComponentContext;
import javax.jbi.component.ComponentLifeCycle;
import javax.jbi.component.ServiceUnitManager;
import javax.jbi.servicedesc.ServiceEndpoint;
import javax.jbi.messaging.MessageExchange;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;



/**
 * Component for Crap.
 *
 *
 */
public class CrapComponent implements Component {
    /**
     * Component Context.
     */
    private ComponentContext compCtx;
    
    /**
     * Component Lifecycle.
     */
    private CrapLifeCycle compLifeCycle;
    
    /**
     * Component SU Manager.
     */
    private CrapSUManager compSUmgr;
    
    /**
     * Logger.
     */
    private Logger log;
    
    /**
     * Creates a new instance of CrapSUManager.
     */
    public CrapComponent(){
        compLifeCycle = new CrapLifeCycle();
    }
    
    /**
     * Get the life cycle control interface for this component. This interface allows
     * the JBI implementation to control the running state of this component.
     * This method must be called before any other methods of this interface are called.
     * In addition, the JBI implementation must call the init() method of the component
     * life cycle returned by this method before calling any other methods on
     * this interface, or the component life cycle interface.
     *
     * @return the life cycle control interface for this component; must be non-null.
     */
    public ComponentLifeCycle getLifeCycle(){
        return compLifeCycle;
    }
    
    /**
     * Get the Service Unit manager for this component. If this component does not support deployments, it must return null.
     *
     * @return the ServiceUnitManager for this component, or null if there is none.
     */
    public ServiceUnitManager getServiceUnitManager() {
        compSUmgr = new CrapSUManager(compLifeCycle.getComponentContext());
        return compSUmgr;
    }
    
    /**
     * Retrieves a DOM representation containing metadata which describes the service provided by this component,
     * through the given endpoint. The result can use WSDL 1.1 or WSDL 2.0. .
     *
     * @param endpoint service endpoint.
     *
     * @return the description for the specified service endpoint.
     */
    public Document getServiceDescription(ServiceEndpoint endpoint) {
        return null;
    }
    
    /**
     * This method is called by JBI to check if this component, in the role of
     * provider of the service indicated by the given exchange, can actually
     * perform the operation desired.
     *
     * @param the endpoint to be used by the consumer; must be non-null.
     * @param the proposed message exchange to be performed; must be non-null.
     *
     * @return  true if this provider component can perform the given exchange with the described consumer.
     */
    public boolean isExchangeWithConsumerOkay(
            ServiceEndpoint endpoint, MessageExchange exchange) {
        return true;
    }
    
    /**
     * This method is called by JBI to check if this component, in the role of
     * consumer of the service indicated by the given exchange, can actually
     * interact with the the provider completely.
     *
     * @param  the endpoint to be used by the provider; must be non-null.
     * @param  the proposed message exchange to be performed; must be non-null.
     *
     * @return  true if this consumer component can interact with the described provider to perform the given exchange.
     */
    public boolean isExchangeWithProviderOkay(
            ServiceEndpoint endpoint,
            MessageExchange exchange) {
        return true;
    }
    
    /**
     * Resolve the given endpoint reference. This is called by JBI when it is attempting
     * to resolve the given EPR on behalf of a component.
     * If this component returns a non-null result, it must conform to the following:
     *
     * This component implements the ServiceEndpoint returned.
     * The result must not be registered or activated with the JBI implementation.
     * Dynamically resolved endpoints are distinct from static ones; they must not
     * be activated (see ComponentContext.activateEndpoint(QName, String)), nor registered
     * (see ComponentContext) by components. They can only be used to address message exchanges;
     * the JBI implementation must deliver such exchanges to the component that resolved
     * the endpoint reference (see ComponentContext.resolveEndpointReference(DocumentFragment)).
     *
     * @param epr the endpoint reference, in some XML dialect understood by the appropriate component (usually a binding); must be non-null.
     *
     * @return  the service endpoint for the EPR; null if the EPR cannot be resolved by this component.
     */
    public ServiceEndpoint resolveEndpointReference(DocumentFragment epr) {
        return null;
    }
    
}
