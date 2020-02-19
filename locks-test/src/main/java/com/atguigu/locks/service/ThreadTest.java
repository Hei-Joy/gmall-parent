package com.atguigu.locks.service;

import sun.nio.ch.ThreadPool;

import java.util.UUID;
import java.util.concurrent.*;

public class ThreadTest {

//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        System.out.println("主线程......");
//        Thread01 thread01 = new Thread01();
//        //异步化。
////        new Thread(thread01).start();
////
////        new Thread(new Thread02()).start();
//
//
//        FutureTask<String> task = new FutureTask<>(new Thread03());
//        new Thread(task).start();
//        System.out.println("主线程哈哈哈。。。。");
//
//        //获取异步运行的结果
//        String s = task.get();//获取结果会等待执行完
//        System.out.println("异步获取到的结果是："+s);
//
//
//        System.out.println("主线程结束......");
//    }

//    public static void main(String[] args) {
//        //使用线程池控制系统资源，防止线程资源耗尽
//        //线程数调多少？cpu内核数。实际：必须通过压力测试来寻找系统最佳参数，
//        //压力。峰值流量*3。100,8,9；  16-8   cpu^3
//        ExecutorService threadPool = Executors.newFixedThreadPool(2);
//
//        //默认的线程池里面的Queue是一个无界队列。
//        //极限情况。线程全部放进队列。无界队列撑爆内存。
//        //ThreadPool：拒绝策略。四种：自己总结;默认出异常。
//
//
//        //
//        System.out.println("线程池任务准备....");
//        for(int i=0;i<10;i++){
//            Thread thread = new Thread(() -> {
//                System.out.println("当前线程开始:" + Thread.currentThread());
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//
//                } catch (Exception e) {
//
//                }
//                int j = 10/0;
//                System.out.println("当前线程结束:" + Thread.currentThread());
//            });
//            //给线程池提交任务
//            threadPool.submit(thread);
//        }
//        System.out.println("所有任务都已提交....");
//    }
//

    /**
     * 任务交给线程池，出现异常无法感知。
     * @param args
     * @throws ExecutionException
     * @throws InterruptedException
     */
//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ExecutorService pool = Executors.newFixedThreadPool(10);
//        System.out.println("主线程......");
//
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程开始："+Thread.currentThread());
//            String uuid = UUID.randomUUID().toString();
//            System.out.println("当前线程结束："+Thread.currentThread());
//            return uuid;
//        }, pool).thenApply((r)->{
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            }catch (Exception e){
//            }
//            System.out.println("上一步的结果是："+r);
//            return r.replace("-","");
//        }).whenComplete((r,e)->{
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            }catch (Exception e1){
//            }
//            System.out.println("最终结果...."+r);
//        });
//
//
//
//
//        System.out.println("主线程结束......");
//
//
//    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(10);

        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品基本数据...");
            return "小米";
        }, pool)
                .whenComplete((r,e)->{
                    System.out.println("结果是："+r);
                });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品属性数据...");
            return 1;
        }, pool).whenComplete((r,e)->{
            System.out.println("结果是："+r);
        });

        CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品营销数据...");
            return "满199减100";
        }, pool).whenComplete((r,e)->{
            System.out.println("结果是："+r);
        });

        //所有人都执行完
        CompletableFuture<Void> allOf = CompletableFuture.allOf(f1, f2, f3);

        //Void aVoid = allOf.get();
       // allOf.join();//线程插队。

        System.out.println("所有人都完成了...."+f1.get());

        // CompletableFuture.anyOf(f1,f2,f3);



        //以后异步任务的编程模式。。。。。
        //CompletableFuture.supplyAsync(()->{},pool).whenComplete()



    }

}


class Thread01 extends Thread{

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){

        }
        System.out.println("Thread01-当前线程"+Thread.currentThread());
    }
}

class Thread02 implements Runnable{

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){

        }
        System.out.println("Thread02-当前线程"+Thread.currentThread());
    }
}

class Thread03 implements Callable<String>{

    @Override
    public String call() throws Exception {
        try {
            System.out.println("Callable开始运行");
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){

        }
        System.out.println("Callable运行结束");
        return "OK";
    }
}