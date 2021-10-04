package com.jsh.juc.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//演示线程池的三种常用分类
public class ThreadPoolDemo {
    public static void main(String[] args) {
        //一池五线程
        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);

        //一池一线程
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();

        //一池可扩容线程
        ExecutorService threadPool3 = Executors.newCachedThreadPool();

        //十个顾客请求
        try {
            for (int i = 0; i < 1000; i++) {
                //执行
                threadPool3.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " 办理业务");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool3.shutdown();
        }
    }
}
