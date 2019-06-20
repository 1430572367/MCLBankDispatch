package com.mcl.window;

import com.mcl.dispatch.Dispatch;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class VIPWin {

    //双重检查锁
    private volatile static VIPWin vIPWindow = null;
    //生成窗口
    public static VIPWin getInstance(){
        if (null==vIPWindow){
            synchronized (Dispatch.class){
                if (null==vIPWindow){
                    vIPWindow=new VIPWin();
                }
            }
        }
        return vIPWindow;
    }

    private ExecutorService pool =Executors.newSingleThreadExecutor();
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(1);
    private AtomicInteger count = new AtomicInteger(1);

    private VIPWin(){}

    public int handle(){
        Future future =null;
            future = pool.submit(new Work(cyclicBarrier, count, "VIP"));
            try {
                return (int) future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        /*future = pool.submit(new Work(cyclicBarrier, count, "VIP"));
        try {
            while (future.isDone()){
            }
            return (int) future.get();
        }catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }*/

        return -2;
    }
}
