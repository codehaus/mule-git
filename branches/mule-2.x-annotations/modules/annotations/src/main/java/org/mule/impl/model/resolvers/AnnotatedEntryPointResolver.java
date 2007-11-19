/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.model.resolvers;

import org.mule.config.annotations.Service;
import org.mule.umo.UMOEventContext;
import org.mule.umo.model.InvocationResult;
import org.mule.util.ClassUtils;
import org.mule.util.StringUtils;

import java.lang.reflect.Method;

/** TODO */
public class AnnotatedEntryPointResolver extends AbstractEntryPointResolver
{
    public InvocationResult invoke(Object component, UMOEventContext context) throws Exception
    {
        Object[] payload = getPayloadFromMessage(context);
        Class[] classTypes = ClassUtils.getClassTypes(payload);

        Method method = getMethodByArguments(component, payload);

        if (method == null)
        {
            if (!component.getClass().isAnnotationPresent(Service.class))
            {
                InvocationResult result = new InvocationResult(InvocationResult.STATE_INVOKE_NOT_SUPPORTED);
                result.setErrorMessage("@Sevice annotation not set on service component: " + component);
                return result;
            }
            Service service = component.getClass().getAnnotation(Service.class);
            if(StringUtils.isEmpty(service.entryPoint()))
            {
                InvocationResult result = new InvocationResult(InvocationResult.STATE_INVOKE_NOT_SUPPORTED);
                result.setErrorMessage("@Service(entryPoint) annotation not set on service component: " + component);
                return result;
            }
            try
            {
                method = component.getClass().getMethod(service.entryPoint(), classTypes);
                this.addMethodByArguments(component, method, payload);
            }
            catch (Exception e)
            {
                //Check for No arg methods
                try
                {
                    method = component.getClass().getMethod(service.entryPoint(), ClassUtils.NO_ARGS_TYPE);
                }
                catch (Exception e1)
                {
                    InvocationResult result = new InvocationResult(InvocationResult.STATE_INVOKED_FAILED);
                    result.setErrorNoMatchingMethods(component, classTypes, this);
                    return result;
                }
            }
        }
        return invokeMethod(component, method,
                (method.getParameterTypes().length==0 ? ClassUtils.NO_ARGS : payload));
    }


    public String toString()
    {
        final StringBuffer sb = new StringBuffer();
        sb.append("AnnotatedEntryPointResolver");
        sb.append("{transformFirst=").append(isTransformFirst());
        sb.append(", acceptVoidMethods=").append(isAcceptVoidMethods());
        sb.append('}');
        return sb.toString();
    }
}
