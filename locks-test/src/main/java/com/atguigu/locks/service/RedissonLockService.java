package com.atguigu.locks.service;

import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.Semaphore;

@Service
public class RedissonLockService {

    @Autowired
    RedissonClient redisson;



    private String hello="hello";

    public boolean lock() {
        RLock lock = redisson.getLock("lock");
        //lock.lock();//默认是阻塞的。
        //lock.tryLock()；是非阻塞的，尝试一下，拿不到就算了。
        //boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);等待100s内只要能获取到锁，这个锁就10秒超时，

        /**
         *
         */
        lock.lock();
        System.out.println("第一次锁");
        lock.lock();
        System.out.println("第二次锁");
        lock.lock();
        System.out.println("第三次锁");
        //这个线程的任意期间都要获取这把锁，是直接使用的。说明这个锁是可重入锁。


        return  lock.tryLock();


        //哪个线程加的锁一定要在这个线程解


        //加锁
        //业务
        //解锁

    }

    /**
     * 错误的场景。
     * 1、两个服务及两个以上服务操作相同数据，如果涉及读写，
     * 读加读锁，写加写锁。
     */
    public void unlock() {
//        RLock lock = redisson.getLock("lock");
//        lock.unlock();
//        RReadWriteLock helloValue = redisson.getReadWriteLock("helloValue");
//        RLock rLock = helloValue.readLock();
//        RLock rLock1 = helloValue.writeLock();
//
//        //修改一号记录
//        rLock1.lock();
//        //修改
//
//        rLock.lock();
    }

    /**
     * 写锁一个，排它锁（独占锁）
     * 读锁是一个共享锁。
     *
     * 有写锁，写锁以后的读都不可以，只有写锁释放才能读。
     *
     * 多个写，有写锁存在，就必须竞争写锁。
     *
     *
     *
     * @return
     */
    public String read() {
        RReadWriteLock helloValue = redisson.getReadWriteLock("helloValue");
        RLock readLock = helloValue.readLock();
        readLock.lock();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String a = hello;


        readLock.unlock();
        return a;
    }

    public String write(){

        RReadWriteLock helloValue = redisson.getReadWriteLock("helloValue");
        RLock writeLock = helloValue.writeLock();
        writeLock.lock();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }

        hello = UUID.randomUUID().toString();
        writeLock.unlock();
        return hello;
    }

    public Boolean tc() throws InterruptedException {

        RSemaphore semaphore = redisson.getSemaphore("tcc");
        semaphore.acquire();
        return true;
    }

    public Boolean rc() {
        RSemaphore semaphore = redisson.getSemaphore("tcc");
        semaphore.release();
        return true;
    }


    public Boolean gogogo() {
        RCountDownLatch downLatch = redisson.getCountDownLatch("num");

        downLatch.countDown();
        System.out.println("溜了....");
        return true;
    }

    public Boolean suomen() throws InterruptedException {
        RCountDownLatch downLatch = redisson.getCountDownLatch("num");
        downLatch.trySetCount(10);

        downLatch.await();//等大家都走完...
        System.out.println("我要锁门....");
        return true;

    }
}
