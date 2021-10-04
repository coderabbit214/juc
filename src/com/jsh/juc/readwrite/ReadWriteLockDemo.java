package com.jsh.juc.readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//资源类
class MyCache {
    private volatile Map<String, String> map = new HashMap<>();

    //创建读写锁对象
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //放数据
    public void put(String key, String value) {
        //添加写锁
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "正在写数据");
            //暂停一会
            TimeUnit.MICROSECONDS.sleep(300);
            //放数据
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写完了" + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放锁
            readWriteLock.writeLock().unlock();
        }
    }

    //取数据
    public String get(String key) {
        //添加读锁
        readWriteLock.readLock().lock();
        String res = null;
        try {
            System.out.println(Thread.currentThread().getName() + "正在读数据");
            //暂停一会
            TimeUnit.MICROSECONDS.sleep(300);
            //放数据
            res = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读完了" + key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放锁
            readWriteLock.readLock().unlock();
        }
        return res;
    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        //创建线程 放数据
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.put(finalI + "", finalI + "");
            }, i + "存").start();
        }

        //创建线程 取数据
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.get(finalI + "");
            }, i + "取").start();
        }
    }
}
