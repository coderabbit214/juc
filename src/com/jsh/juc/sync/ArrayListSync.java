package com.jsh.juc.sync;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/*
ArrayList线程不安全
 */

public class ArrayListSync {
    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
        //1.Vector
//        List<String> list = new Vector<>();
        //2.Collections
//        List<String> list = Collections.synchronizedList(new ArrayList<>());
        //CopyOnWriteArrayList
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 400; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }).start();
        }
    }
}
