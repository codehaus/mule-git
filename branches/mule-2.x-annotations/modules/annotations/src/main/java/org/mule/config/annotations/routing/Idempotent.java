package org.mule.config.annotations.routing;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <code>IdempotentReceiver</code> ensures that only unique messages are received
 * by a component. There are different types of Idempotancy handlers
 * <ul>
 * <li>ID - checks the unique id of the incoming message. Note that the underlying
 * endpoint must support unique message Ids for this to
 * work, otherwise a <code>UniqueIdNotSupportedException</code> is thrown.</li>
 * <li>HASH - calculates the SHA-256 hash of the message itself. This provides a
 * value with an infinitesimally small chance of a collision.</li>
 * </ul>
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent
{
    public enum Type { ID, HASH }

    /**
     * The type of Idempotent router to use
     * @return the type of Idempotent router to use
     */
    Type type() default Type.ID;

}
