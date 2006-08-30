/*
 * Copyright (C) 2005 Wael Chatila. All Rights Reserved.
 * Permission to use, read, view, run, copy, compile, link or any
 * other way use this code without prior permission
 * of the copyright holder is not permitted.
 */
package com.icegreen.greenmail.util;

import java.util.Vector;
/**
 * A synchronized blocking FIFO queue.
 * <P>
 * @author Wael
 */
public class BlockingFifoQueue extends Object
{
 protected Vector m_queue = new Vector(1000,20);
 private boolean m_stopped = false;
 //=============================================

 /**
  * Constructor
  */
 public BlockingFifoQueue()
 {
 }

 public boolean isStopped()
 {
   return m_stopped;
 }

 public synchronized void shutDownQueue()
 {
   m_stopped = true;
   notifyAll();
 }
 /*
  * Add an element to the queue.
  */
 public synchronized void push(Object obj)
 {
    m_queue.add(obj);
    notifyAll();
 }

 /*
  * Retrives the next element in the queue. If no element in queue this function will block the callers thread until an element gets added or shutDownQueue() is called
  * @return the next object in the queue.
  * If shutDownQueue() has been called and the queue is empty null will be returned. Use the public function isStoppedto distinguish this from a null element in the queue
  */
 public synchronized Object pop() throws InterruptedException
 {
   while (m_queue.size() == 0)
   {
     if (m_stopped)
     {
        return null;
     }
     wait();
   }
   return m_queue.remove(0);
 }
}
