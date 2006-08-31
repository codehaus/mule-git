
package com.icegreen.greenmail.util;

public abstract class ThreadPoolWorker implements Runnable
{
    private BlockingFifoQueue taskQueue;
    private String name = "Thread Pool Worker";
    private volatile boolean shuttingDown = false;

    public void setQueue(BlockingFifoQueue taskQueue)
    {
        this.taskQueue = taskQueue;
    }

    public void shutDown()
    {
        shuttingDown = true;
    }

    final public void run()
    {
        while (!shuttingDown)
        {
            try
            {
                doWork(taskQueue.pop());
            }
            catch (InterruptedException e)
            {
                // if shuttingDown is true then this InterruptedException has been
                // called for the thread to close
                if (!shuttingDown)
                {
                    // mark this thread as dead
                    shuttingDown = true;
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public abstract void doWork(Object o);

}
