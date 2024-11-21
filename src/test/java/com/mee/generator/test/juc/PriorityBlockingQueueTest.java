package com.mee.generator.test.juc;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.LockSupport;

/**
 * queueTest
 *
 * @author shadow
 * @version 1.0
 * @className PriorityBlockingQueueTest
 * @date 2024/3/19 14:19
 */
public class PriorityBlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {
        PriorityBlockingQueue<Patient> queue = new PriorityBlockingQueue<Patient>();
        for(int i=0;i<10;i++){
            queue.offer(new Patient("patient"+i,30+i));
        }
        Patient oldMan = new Patient("oldMan", 88);
        queue.offer(oldMan);

        while (!queue.isEmpty()){
            Patient item= queue.take();
            System.out.println(item.name+"->"+item.age);
        }

    }

    static class Patient implements Comparable<Patient>{
        private String name;
        private Integer age;
        private long waitingTime;

        public Patient(String name, Integer age) {
            this.name = name;
            this.age = age;
            this.waitingTime=System.nanoTime();
        }

        @Override
        public int compareTo(Patient o) {
//            return o.age-this.age;
            if(this.age>=80){
                return -1;
            }else if(o.age>=80){
                return 1;
            }
            Thread thread = new Thread();
            thread.run();
            return this.waitingTime<o.waitingTime?-1:1;
        }
    }
}
