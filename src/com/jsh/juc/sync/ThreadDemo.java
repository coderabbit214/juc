package com.jsh.juc.sync;


class Share {
    //初始值
    private int number = 0;

    //+1的方法
    public synchronized void add() throws InterruptedException {
        while (number != 0) {
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName() + "::" + number);
        this.notifyAll();
    }

    //+1的方法
    public synchronized void dno() throws InterruptedException {
        while (number == 0) {
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "::" + number);
        this.notifyAll();
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

