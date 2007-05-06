/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the MuleSource MPL
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.impl.lifecycle;

import org.mule.config.i18n.Message;
import org.mule.config.spring.RegistryFacade;
import org.mule.umo.UMOException;
import org.mule.umo.UMOManagementContext;
import org.mule.umo.lifecycle.LifecycleException;
import org.mule.umo.lifecycle.UMOLifecyclePhase;
import org.mule.util.ClassUtils;
import org.mule.util.StringMessageUtils;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * TODO
 */
public class LifecyclePhase implements UMOLifecyclePhase
{
    /**
     * logger used by this class
     */
    protected transient final Log logger = LogFactory.getLog(LifecyclePhase.class);

    private Class lifecycleClass;
    private Method lifecycleMethod;
    private Set orderedLifecycleObjects = new LinkedHashSet(6);
    private Class[] ignorredObjectTypes;
    private String name;
    private Set supportedPhases;
    private int registryScope = RegistryFacade.SCOPE_REMOTE;

    public LifecyclePhase(String name, Class lifecycleClass)
    {
        this.name = name;
        this.lifecycleClass = lifecycleClass;
        //LifecyclePhase interface only has one method
        lifecycleMethod = lifecycleClass.getMethods()[0];
    }

    public void fireLifecycle(UMOManagementContext managementContext, String currentPhase) throws UMOException
    {
        if(logger.isDebugEnabled()) logger.debug("Attempting to fire lifecycle phase: " + getName());
        if(currentPhase.equals(name))
        {
            if(logger.isDebugEnabled()) logger.debug("Not firing, already in lifecycle phase: " + getName());
            return;
        }
        if(!isPhaseSupported(currentPhase))
        {
            throw new IllegalStateException("Lifecycle phase: " + name + " does not support current phase: "
                    + currentPhase + ". Phases supported are: " + StringMessageUtils.toString(supportedPhases));
        }
        boolean fireDefault = true;
        Set called = new HashSet();
        for (Iterator iterator = orderedLifecycleObjects.iterator(); iterator.hasNext();)
        {
            LifecycleObject lo = (LifecycleObject) iterator.next();
            if (lo.getType().equals(getLifecycleClass()))
            {
                fireDefault = false;
            }
            Map objects = managementContext.getRegistry().lookupCollection(lo.getType(), getRegistryScope());
            if (objects != null && objects.size() > 0)
            {
                lo.firePreNotification(managementContext);

                for (Iterator iterator1 = objects.values().iterator(); iterator1.hasNext();)
                {
                    Object o = iterator1.next();
                    if (called.contains(new Integer(o.hashCode())))
                    {
                        continue;
                    }
                    if(logger.isDebugEnabled()) logger.debug("lifecycle phase: " + getName() + " for object: " + o);                                    

                    applyLifecycle(o);
                    called.add(new Integer(o.hashCode()));
                }

                lo.firePostNotification(managementContext);
            }

        }
        if (fireDefault)
        {
            //TODO
        }

    }

    public void addOrderedLifecycleObject(LifecycleObject lco)
    {
        orderedLifecycleObjects.add(lco);
    }

    public void removeOrderedLifecycleObject(LifecycleObject lco)
    {
        orderedLifecycleObjects.remove(lco);
    }

    protected boolean ignoreType(Class type)
    {
        if (ignorredObjectTypes == null)
        {
            return false;
        }
        else
        {
            for (int i = 0; i < ignorredObjectTypes.length; i++)
            {
                Class ignorredObjectType = ignorredObjectTypes[i];
                if (ignorredObjectType.isAssignableFrom(type))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public Set getOrderedLifecycleObjects()
    {
        return orderedLifecycleObjects;
    }

    public void setOrderedLifecycleObjects(Set orderedLifecycleObjects)
    {
        this.orderedLifecycleObjects = orderedLifecycleObjects;
    }

    public Class[] getIgnorredObjectTypes()
    {
        return ignorredObjectTypes;
    }

    public void setIgnorredObjectTypes(Class[] ignorredObjectTypes)
    {
        this.ignorredObjectTypes = ignorredObjectTypes;
    }

    public Class getLifecycleClass()
    {
        return lifecycleClass;
    }

    public void setLifecycleClass(Class lifecycleClass)
    {
        this.lifecycleClass = lifecycleClass;
    }

    public String getName()
    {
        return name;
    }

    public Set getSupportedPhases()
    {
        return supportedPhases;
    }

    public void setSupportedPhases(Set supportedPhases)
    {
        this.supportedPhases = supportedPhases;
    }

    public void registerSupportedPhase(String phase)
    {
        if(supportedPhases==null) supportedPhases = new HashSet();
        supportedPhases.add(phase);
    }

    public boolean isPhaseSupported(String phase)
    {
        if(getSupportedPhases()==null)
        {
            return true;
        }
        else if(getSupportedPhases().contains(ALL_PHASES))
        {
            return true;
        }
        else
        {
            return getSupportedPhases().contains(phase);
        }
    }

    public void applyLifecycle(Object o) throws LifecycleException
    {
        if(o==null) return;

        if (ignoreType(o.getClass()))
        {
            return;
        }
        if(!getLifecycleClass().isAssignableFrom(o.getClass()))
        {
            return;
        }
        try
        {
            lifecycleMethod.invoke(o, ClassUtils.NO_ARGS);
        }
        catch (Exception e)
        {
            throw new LifecycleException(Message.createStaticMessage("Failed to invoke lifecycle method: " + lifecycleMethod + " on object: " + o), e, this);
        }
    }

    public int getRegistryScope()
    {
        return registryScope;
    }

    public void setRegistryScope(int registryScope)
    {
        this.registryScope = registryScope;
    }
}




