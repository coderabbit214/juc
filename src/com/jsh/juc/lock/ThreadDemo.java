package com.jsh.juc.lock;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Share {
    //初始值
    private int number = 0;

    //创建lock
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //+1的方法
    public void add() throws InterruptedException {
        //上锁
        lock.lock();
        try {
            //判断（使用while不使用if）
            while (number != 0) {
                //等待
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "::" + number);
            //通知
            condition.signalAll();
        } finally {
            //解锁
            lock.unlock();
        }


    }

    //+1的方法
    public void dno() throws InterruptedException {
        //上锁
        lock.lock();
        try {
            while (number == 0) {
                //等待
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "::" + number);
            //通知
            condition.signalAll();
        } finally {
            //解锁
            lock.unlock();
        }
    }
}

public class ThreadDemo {
    public static void main(String[] args) {
        Share sale = new Share();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                try {
                    sale.add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                try {
                    sale.dno();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                try {
                    sale.add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();
    }
}

