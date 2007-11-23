package org.mule.config.annotations.notifications;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Documented;

/**
 * An annotation that tells Mule that this object is interested in receiving {@link org.mule.umo.manager.UMOServerNotification} events.
 * These are internal Mule events that fire whensomething happens in Mule i.e. a Component is registered or there is a
 * security exception. Objects that use this class must implement {@link org.mule.umo.manager.UMOServerNotificationListener}.
 *
 * This annotation allows developer to specify a 'subscription' string. This is a wildcard string that will be matched against
 * the {@link org.mule.umo.manager.UMOServerNotification#getResourceIdentifier()} of the notification. The Resource Identifier is
 * usually the name of the object that caused the notification. You'll need to look at the JavaDoc for the Notification you are
 * interested in in order know exactly what the Resource Identifier might be.
 *
 * @see org.mule.umo.manager.UMOServerNotification
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotificationListener
{
    /**
     * This is a wildcard string that will be matched against
     * the {@link org.mule.umo.manager.UMOServerNotification#getResourceIdentifier()} of the notification. The Resource Identifier is
     * usually the name of the object that caused the notification. You'll need to look at the JavaDoc for the Notification you are
     * interested in in order know exactly what the Resource Identifier might be.
     * @return A string that describes the the wilccard pattern to use to receive events or null if one is not set. If null
     * All events of the implemented listener type will be received
     */
    String subscription() default "";
}
