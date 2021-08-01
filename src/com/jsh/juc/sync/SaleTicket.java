package com.jsh.juc.sync;

//资源类
class Ticket{
    private int number = 30;
    public synchronized void sale(){
        if (number>0) {
            System.out.println(Thread.currentThread().getName()+"::"+number--+"还剩"+number);
        }
    }
}



public class SaleTicket {
    public static void main(String[] args) {

        Ticket ticket = new Ticket();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<=40;i++){
                    ticket.sale();
                }
            }
        },
                "AA").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<=40;i++){
                    ticket.sale();
                }
            }
        },"BB").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<=40;i++){
                    ticket.sale();
                }
            }
        },"CC").start();
    }
}
