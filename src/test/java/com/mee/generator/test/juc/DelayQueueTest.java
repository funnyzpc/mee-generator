package com.mee.generator.test.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * DelayQueueTest
 *
 * @author shadow
 * @version 1.0
 * @className DelayQueueTest
 * @date 2024/3/20 10:51
 */
public class DelayQueueTest {

    private  ReentrantLock lock = new ReentrantLock(true);

    @Test
    public void test01(){
        final ReentrantLock lock1 = this.lock;
        lock1.lock();
        try{
//            DelayQueue;
//            ConcurrentSkipListMap;

        }finally {
            lock1.unlock();
        }

    }



}
