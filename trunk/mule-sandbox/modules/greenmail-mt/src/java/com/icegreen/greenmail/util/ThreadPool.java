
package com.icegreen.greenmail.util;

public class ThreadPool
{
    private int threadBound;
    private Thread[] threads;
    private ThreadPoolWorker[] workers;
    private BlockingFifoQueue taskQueue = new BlockingFifoQueue();
    private boolean shutDown = false;

    public ThreadPool(ThreadPoolWorker[] workers)
    {
        this.threadBound = workers.length;
        this.workers = workers;
        threads = new Thread[this.threadBound];
        for (int i = 0; i < threadBound; i++)
        {
            workers[i].setQueue(taskQueue);
            threads[i] = new Thread(workers[i]);
            threads[i].setDaemon(true);
            threads[i].setName(workers[i].getName());
        }
    }

    public synchronized void start()
    {
        for (int i = 0; i < threadBound; i++)
        {
            threads[i].start();
        }
    }

    public synchronized void addTaskObject(Object taskObject)
    {
        this.taskQueue.push(taskObject);
    }

    public synchronized void shutdown()
    {
        if (this.shutDown)
        {
            for (int i = 0; i < threadBound; i++)
            {
                this.workers[i].shutDown(); // mark as shutdown
                this.threads[i].interrupt(); // wake up from blocking queue
            }
            this.shutDown = true;
        }
    }

}
