package org.mule.config.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that allows developers to configure an inbound endpoint for the service. Developers can use more than
 * one annotation to add more endpoints to a component.
 * For some types of endpoint such as Jms, and Jms connection is required to be available in the Registry when
 * this service is initialised. You can specify the connector name directly if there is more than one Jms connector
 * in the registry (i.e. one transactional, the other not).
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OutboundEndpoint
{
    /**
     * The name or URI address of the endpoint to use.  The URI would be litteral and its not recommended that
     * literal URIs are used. Instead you can use either the name of a global endpoint that is already available
     * in the registry or you can use a property placeholder and the real value will be injected at runtime. For
     * example -
     * <code>@OutboundEndpoint(endpoint = "${my.endpoint}")</code>
     * My endpoint would then be resolved to a property called 'my.endpoint' that is regeistered with the registry.
     *
     * @return A string representation of the endpoint URI, name or property placeholder
     */
    String endpoint();

    /**
     * Determines if requests originating from this endpoint should be synchronous
     * i.e. execute in a single thread and possibly return an result. This property
     * is only used when the endpoint is of type 'receiver'
     *
     * @return whether requests on this endpoint should execute in a single thread.
     *         This property is only used when the endpoint is of type 'receiver'
     */
    boolean synchronous() default false;

    /**
     * For certain providers that support the notion of a backchannel such as sockets
     * (outputStream) or Jms (ReplyTo) Mule can automatically wait for a response
     * from a backchannel when dispatching over these protocols. This is different
     * for synchronous as synchronous behavior only applies to in
     *
     */
    boolean remoteSynchronous() default false;

    /**
     * The connector namethat will be used to receive events. It is important that
     * the endpoint prototcol and the connector correlate i.e. if your endpoint is a jms
     * queue your connector must be a JMS Connector.
     * Many transports such as http do not need a connector to be present since Mule can create
     * a default one as needed.
     *
     * @return the connector name associated with the endpoint
     */
    String connectorName() default "";

    /**
     * Decides the encoding to be used for events received by this endpoint
     *
     * @return the encoding set on the endpoint or null if no codin has been
     *         specified
     */
    String encoding() default "";

    /**
     * A comma separate list of key/value pairs i.e.
     * <code>"apple=green, banana=yellow"</code>
     * Property placehders can be used in these values i.e.
     * <code>"apple=${apple.color}, banana=yellow"</code>
     *
     * @return A comma separate list of key/value pairs or an empty string if no
     * properties re set
     */
    String properties() default "";

    /**
     * Transformers are responsible for transforming data when it is sent after
     * annotate service has finished processing.
     * Transformers should be listed as a comma separate list of registered transformers.
     * Property placeholders can be used to load transforms based on external values
     * @return the transformers to use when receiving data
     */
    String transformers() default "";

}