package com.mcl.window;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import com.mcl.dispatch.Dispatch;

public class ExpWin {

    //双重检查锁
    private volatile static ExpWin expWindow = null;
    //生成窗口
    public  static ExpWin getInstance(){
        if (null==expWindow){
            synchronized (Dispatch.class){
                if (null==expWindow){
                    expWindow=new ExpWin();
                }
            }
        }
        return expWindow;
    }

    private ExecutorService pool =Executors.newSingleThreadExecutor();
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(1);
    private AtomicInteger count = new AtomicInteger(1);

    private ExpWin(){}

    public int handle(){
        Future future =null;
            future = pool.submit(new Work(cyclicBarrier, count, "Express"));
            try {
                return (int) future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        /*future = pool.submit(new Work(cyclicBarrier, count, "Express"));
        try {
            while (!future.isDone()){
            }
            return (int) future.get();
        }catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }*/

        return -2;
    }
}
