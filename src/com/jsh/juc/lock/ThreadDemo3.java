package com.jsh.juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
线程间定制化通知
 */
//第一步 创建资源类
class ShareResource { 
    //定义标志位
    private int flag = 1;//1:aa,2:bb,3:cc
    //创建lock锁
    private Lock lock = new ReentrantLock();

    // 创建三个Condition
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    //打印5次，参数第几轮
    public void print5(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while (flag != 1) {
                c1.await();
            }
            //干活
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+"::"+i+"第几轮："+loop);
            }
            //通知
            flag = 2;
            c2.signal();
        }finally {
            lock.unlock();
        }
    }

    //打印10次，参数第几轮
    public void print10(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while (flag != 2) {
                c2.await();
            }
            //干活
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName()+"::"+i+"第几轮："+loop);
            }
            //通知
            flag = 3;
            c3.signal();
        }finally {
            lock.unlock();
        }
    }

    //打印15次，参数第几轮
    public void print15(int loop) throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断
            while (flag != 3) {
                c3.await();
            }
            //干活
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName()+"::"+i+"第几轮："+loop);
            }
            //通知
            flag = 1;
            c1.signal();
        }finally {
            lock.unlock();
        }
    }

}



public class ThreadDemo3 {

    public static void main(String[] args) {
        ShareResource shareResource = new ShareResource();
        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    shareResource.print5(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"aa").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    shareResource.print10(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"bb").start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    shareResource.print15(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"cc").start();
    }
}
