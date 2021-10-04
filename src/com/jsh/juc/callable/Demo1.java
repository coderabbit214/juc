package com.jsh.juc.callable;


import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyThread implements Callable{

    @Override
    public Object call() throws Exception {
        return 200;
    }
}


public class Demo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //方法一
        //FutureTask 未来任务
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread());

        //方法二
        //lam表达式
        FutureTask<Integer> futureTask2 = new FutureTask<>(() ->{
            System.out.println(Thread.currentThread().getName()+"2");
            return 1024;
        });

        new Thread(futureTask2,"lucy").start();

        //线程是否结束
        while (!futureTask2.isDone()) {
            System.out.println("wait。。。。。");
        }

        //第一次调用 计算 返回结果
        System.out.println(futureTask2.get());

        //第二次调用直接返回结果
        System.out.println(futureTask2.get());
    }
}
