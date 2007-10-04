/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSource, Inc.  All rights reserved.  http://www.mulesource.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */

package org.mule.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.emory.mathcs.backport.java.util.concurrent.ConcurrentSkipListMap;
import edu.emory.mathcs.backport.java.util.concurrent.CopyOnWriteArrayList;
import edu.emory.mathcs.backport.java.util.concurrent.locks.ReadWriteLock;
import edu.emory.mathcs.backport.java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This is a modified version of the ExpiringMap from Apache Mina
 * (http://mina.apache.org/), backported to JDK 1.4 and modified to keep the underlying
 * map ordered by key through a pluggable Comparator. Also a maximum size can be set so
 * that periodic expiration shrinks the map to a defined size, preventing OOM errors. <br>
 * Please note that these two capabilities are essentially contradictory: in order for the
 * map to be bounded, it may be necessary to purge entries who have not yet exceeded their
 * TTL. The bounded size is only lazily enforced through the periodic expiry mechanism,
 * allowing the map to have "too many" elements between intervals.
 * 
 * @see {@link ConcurrentSkipListMap}
 */
public class ExpiringSkipListMap implements Map
{
    public static final int DEFAULT_TIME_TO_LIVE = 60;

    public static final int DEFAULT_EXPIRATION_INTERVAL = 1;

    private static volatile int expirerCount = 1;

    private final ConcurrentSkipListMap delegate;

    private final CopyOnWriteArrayList expirationListeners;

    private final Expirer expirer;

    private volatile int maxSize = -1;

    public ExpiringSkipListMap()
    {
        this(DEFAULT_TIME_TO_LIVE, DEFAULT_EXPIRATION_INTERVAL);
    }

    public ExpiringSkipListMap(int timeToLive)
    {
        this(timeToLive, DEFAULT_EXPIRATION_INTERVAL);
    }

    public ExpiringSkipListMap(int timeToLive, int expirationInterval)
    {
        this(new ConcurrentSkipListMap(), new CopyOnWriteArrayList(), timeToLive, expirationInterval);
    }

    public ExpiringSkipListMap(int timeToLive, int expirationInterval, Comparator keyComparator)
    {
        this(new ConcurrentSkipListMap(keyComparator), new CopyOnWriteArrayList(), timeToLive,
            expirationInterval);
    }

    private ExpiringSkipListMap(ConcurrentSkipListMap delegate,
                                CopyOnWriteArrayList expirationListeners,
                                int timeToLive,
                                int expirationInterval)
    {
        this.delegate = delegate;
        this.expirationListeners = expirationListeners;

        this.expirer = new Expirer();
        expirer.setTimeToLive(timeToLive);
        expirer.setExpirationInterval(expirationInterval);
    }

    public Object put(Object key, Object value)
    {
        ExpiringObject answer = (ExpiringObject) delegate.put(key, new ExpiringObject(key, value,
            System.currentTimeMillis()));
        if (answer == null)
        {
            return null;
        }

        return answer.getValue();
    }

    public Object get(Object key)
    {
        ExpiringObject object = (ExpiringObject) delegate.get(key);

        if (object != null)
        {
            object.setLastAccessTime(System.currentTimeMillis());

            return object.getValue();
        }

        return null;
    }

    public Object remove(Object key)
    {
        ExpiringObject answer = (ExpiringObject) delegate.remove(key);
        if (answer == null)
        {
            return null;
        }

        return answer.getValue();
    }

    public boolean containsKey(Object key)
    {
        return delegate.containsKey(key);
    }

    public boolean containsValue(Object value)
    {
        return delegate.containsValue(value);
    }

    public int size()
    {
        return delegate.size();
    }

    public boolean isEmpty()
    {
        return delegate.isEmpty();
    }

    public void clear()
    {
        delegate.clear();
    }

    // @Override
    public int hashCode()
    {
        return delegate.hashCode();
    }

    public Set keySet()
    {
        return delegate.keySet();
    }

    // @Override
    public boolean equals(Object obj)
    {
        return delegate.equals(obj);
    }

    public void putAll(Map inMap)
    {
        for (Iterator i = inMap.entrySet().iterator(); i.hasNext();)
        {
            Map.Entry e = (Map.Entry) i.next();
            this.put(e.getKey(), e.getValue());
        }
    }

    public Collection values()
    {
        throw new UnsupportedOperationException();
    }

    public Set entrySet()
    {
        throw new UnsupportedOperationException();
    }

    public void addExpirationListener(ExpirationListener listener)
    {
        expirationListeners.add(listener);
    }

    public void removeExpirationListener(ExpirationListener listener)
    {
        expirationListeners.remove(listener);
    }

    public Expirer getExpirer()
    {
        return expirer;
    }

    public int getExpirationInterval()
    {
        return expirer.getExpirationInterval();
    }

    public int getTimeToLive()
    {
        return expirer.getTimeToLive();
    }

    public int getMaxSize()
    {
        return maxSize;
    }

    public void setExpirationInterval(int expirationInterval)
    {
        expirer.setExpirationInterval(expirationInterval);
    }

    public void setTimeToLive(int timeToLive)
    {
        expirer.setTimeToLive(timeToLive);
    }

    public void setMaxSize(int newMaxSize)
    {
        // TODO MULE-1300 expire first!
        this.maxSize = newMaxSize;
    }

    protected class ExpiringObject
    {
        private Object key;
        private Object value;

        private long lastAccessTime;

        private final ReadWriteLock lastAccessTimeLock = new ReentrantReadWriteLock();

        ExpiringObject(Object key, Object value, long lastAccessTime)
        {
            if (value == null)
            {
                throw new IllegalArgumentException("An expiring object cannot be null.");
            }

            this.key = key;
            this.value = value;
            this.lastAccessTime = lastAccessTime;
        }

        public long getLastAccessTime()
        {
            lastAccessTimeLock.readLock().lock();

            try
            {
                return lastAccessTime;
            }
            finally
            {
                lastAccessTimeLock.readLock().unlock();
            }
        }

        public void setLastAccessTime(long lastAccessTime)
        {
            lastAccessTimeLock.writeLock().lock();

            try
            {
                this.lastAccessTime = lastAccessTime;
            }
            finally
            {
                lastAccessTimeLock.writeLock().unlock();
            }
        }

        public Object getKey()
        {
            return key;
        }

        public Object getValue()
        {
            return value;
        }

        // @Override
        public boolean equals(Object obj)
        {
            return value.equals(obj);
        }

        // @Override
        public int hashCode()
        {
            return value.hashCode();
        }
    }

    public class Expirer implements Runnable
    {
        private final ReadWriteLock stateLock = new ReentrantReadWriteLock();

        private long timeToLiveMillis;

        private long expirationIntervalMillis;

        private boolean running = false;

        private final Thread expirerThread;

        public Expirer()
        {
            expirerThread = new Thread(this, "ExpiringMapExpirer-" + expirerCount++);
            expirerThread.setDaemon(true);
        }

        public void run()
        {
            while (running)
            {
                processExpires();

                try
                {
                    Thread.sleep(expirationIntervalMillis);
                }
                catch (InterruptedException e)
                {
                    // can be ignored - we're just a daemon thread
                }
            }
        }

        // TODO MULE-1300
        // implement bounded size by polling the oldest elements off the queue
        // if size > maxSize
        protected void processExpires()
        {
            long timeNow = System.currentTimeMillis();

            for (Iterator values = delegate.values().iterator(); values.hasNext();)
            {
                ExpiringObject o = (ExpiringObject) values.next();

                if (timeToLiveMillis <= 0)
                {
                    continue;
                }

                long timeIdle = timeNow - o.getLastAccessTime();

                if (timeIdle >= timeToLiveMillis)
                {
                    delegate.remove(o.getKey());

                    for (Iterator listeners = expirationListeners.iterator(); listeners.hasNext();)
                    {
                        ((ExpirationListener) listeners.next()).expired(o.getValue());
                    }
                }
            }
        }

        public void startExpiring()
        {
            stateLock.writeLock().lock();

            try
            {
                if (!running)
                {
                    running = true;
                    expirerThread.start();
                }
            }
            finally
            {
                stateLock.writeLock().unlock();
            }
        }

        public void startExpiringIfNotStarted()
        {
            stateLock.readLock().lock();
            try
            {
                if (running)
                {
                    return;
                }
            }
            finally
            {
                stateLock.readLock().unlock();
            }

            stateLock.writeLock().lock();
            try
            {
                if (!running)
                {
                    running = true;
                    expirerThread.start();
                }
            }
            finally
            {
                stateLock.writeLock().unlock();
            }
        }

        public void stopExpiring()
        {
            stateLock.writeLock().lock();

            try
            {
                if (running)
                {
                    running = false;
                    expirerThread.interrupt();
                }
            }
            finally
            {
                stateLock.writeLock().unlock();
            }
        }

        public boolean isRunning()
        {
            stateLock.readLock().lock();

            try
            {
                return running;
            }
            finally
            {
                stateLock.readLock().unlock();
            }
        }

        public int getTimeToLive()
        {
            stateLock.readLock().lock();

            try
            {
                return (int) timeToLiveMillis / 1000;
            }
            finally
            {
                stateLock.readLock().unlock();
            }
        }

        public void setTimeToLive(long timeToLive)
        {
            stateLock.writeLock().lock();

            try
            {
                this.timeToLiveMillis = timeToLive * 1000;
            }
            finally
            {
                stateLock.writeLock().unlock();
            }
        }

        public int getExpirationInterval()
        {
            stateLock.readLock().lock();

            try
            {
                return (int) expirationIntervalMillis / 1000;
            }
            finally
            {
                stateLock.readLock().unlock();
            }
        }

        public void setExpirationInterval(long expirationInterval)
        {
            stateLock.writeLock().lock();

            try
            {
                this.expirationIntervalMillis = expirationInterval * 1000;
            }
            finally
            {
                stateLock.writeLock().unlock();
            }
        }
    }

    public interface ExpirationListener
    {
        void expired(Object expiredObject);
    }

}
