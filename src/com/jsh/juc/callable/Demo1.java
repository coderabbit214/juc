package com.jsh.juc.callable;


import java.util.concurrent.Callable;
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
        //FutureTask
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread());

        //lam表达式
        FutureTask<Integer> futureTask2 = new FutureTask<>(() ->{
            System.out.println(Thread.currentThread().getName()+"2");
            return 1024;
        });

        new Thread(futureTask2,"lucy").start();

        while (!futureTask2.isDone()) {
            System.out.println("wait");
        }

        System.out.println(futureTask2.get());

        System.out.println(futureTask2.get());
    }
}
