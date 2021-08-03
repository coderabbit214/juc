package com.jsh.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

class LTicket{
    private int number = 30;
    private final ReentrantLock lock = new ReentrantLock(true);
    public void sale(){

        lock.lock();
        try {
            if (number>0){
                System.out.println(Thread.currentThread().getName()+"::"+number--+"剩余："+number);
            }
        } finally {
            lock.unlock();
        }
    }
}

public class LSaleTicket {
    public static void main(String[] args) {

        LTicket ticket = new LTicket();
        new Thread(() -> {
            for (int i=0;i<40;i++) {
                ticket.sale();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i=0;i<40;i++) {
                ticket.sale();
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i=0;i<40;i++) {
                ticket.sale();
            }
        }, "CC").start();
    }
}