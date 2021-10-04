package com.jsh.juc.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

//
class Mytask extends RecursiveTask<Integer> {
    //拆分差值不超过10,计算10以内的运算
    private static final Integer VALUE = 10;
    private int begin; //拆分开始值
    private int end; //拆分结束值
    private int result; //返回结果

    public Mytask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if ((end - begin) < VALUE) {
            //相加
            for (int i = begin; i <= end; i++) {
                result += i;
            }
        } else {//进一步拆分
            int middle = (begin + end) / 2;
            Mytask taskLeft = new Mytask(begin, middle);
            Mytask taskRight = new Mytask(middle+1, end);

            taskLeft.fork();
            taskRight.fork();
            result = taskLeft.join()+taskRight.join();
        }
        return result;
    }
}

public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建MyTask对象
        Mytask mytask = new Mytask(0,100);
        //创建分支合并池对象
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> submit = forkJoinPool.submit(mytask);
        //获取结果
        Integer integer = submit.get();
        System.out.println(integer);
        //关闭池对象
        forkJoinPool.shutdown();
    }
}
