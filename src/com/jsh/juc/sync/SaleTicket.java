package com.jsh.juc.sync;

//创建资源类
class Ticket{
    //票数
    private int number = 30;
    //操作方法：卖票
    public synchronized void sale(){
        //判断：是否有票
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
