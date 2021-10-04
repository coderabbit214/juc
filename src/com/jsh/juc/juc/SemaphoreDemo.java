package com.jsh.juc.juc;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号灯
 */
//模拟六辆汽车，停3个停车位
public class SemaphoreDemo {
    public static void main(String[] args) {
        //模拟三个车位
        Semaphore semaphore = new Semaphore(3);

        //模拟6辆汽车
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                try {
                    //占车位
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"抢到了车位");

                    //设置随机停车时间
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                    System.out.println(Thread.currentThread().getName()+"----------离开了车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //释放车位
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
