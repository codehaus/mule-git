/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.config.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;

/**
 * An annotation that defines a Mule service. Objects registered with this annotation will
 * be configured as a service in Mule
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Service
{
    /**
     * The name of the entry point method to use when calling this service. If left blank,
     * Mule will use reflection to determine the entrypoint method
     * @return the method name to call when invoking this service or an empty string if
     * this property has not been set
     */
    String entryPoint() default "";

    /**
     * The name of this service
     * @return the name of this service. This value is required
     */
    String name();

    /**
     * Determines if this service will be a singleton or prototype.
     * Note this refers to the actual service object instance
     * @return true if the service is a singleton
     */
    boolean singleton() default false;
}
