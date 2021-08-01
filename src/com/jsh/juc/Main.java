package com.jsh.juc;

public class Main {
    public static void main(String[] args) {
        Thread aa = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
            while (true) {

            }
        }, "aa");
        aa.setDaemon(true);
        aa.start();


        //主线程
        System.out.println(Thread.currentThread().getName()+"::"+"over");
    }
}
