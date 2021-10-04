package com.jsh.juc.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //异步调用 没有返回值
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(()->{
            System.out.println(Thread.currentThread().getName()+"completableFuture1");

        });
        completableFuture1.get();

        //异步调用 有返回值
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"completableFuture2");
            //模拟异常
            int a = 1/0;
            return 2;
        });
        Integer integer = completableFuture2.whenComplete((t,u)->{
            System.out.println(t); // 2：方法返回值
            System.out.println(u); // null：异常信息
        }).get();
        System.out.println(integer);
    }
}
