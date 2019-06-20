package com.mcl.window;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import com.mcl.dispatch.Dispatch;

/*
*
* 待改进：构造方法就取数据放入集合中，handle方法取数据给服务端响应。
*
* */
public class ComWin {

    //双重检查锁
    private volatile static ComWin comWindow = null;
    //构造器生成窗口
    public static ComWin getInstance(){
        if (null==comWindow){
            synchronized (Dispatch.class){
                if (null==comWindow){
                    comWindow=new ComWin();
                }
            }
        }
        return comWindow;
    }

    private ExecutorService pool =Executors.newFixedThreadPool(10);
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
    private AtomicInteger count = new AtomicInteger(1);

    private ComWin(){}

    public int handle(){
        Future future =null;
        future = pool.submit(new Work(cyclicBarrier, count, "Express"));
        try {
            return (int) future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        /*for (int i = 0; i <10 ; i++) {
                future = pool.submit(new Work(cyclicBarrier, count, "Common"));
                try {
                    while (!future.isDone()){
                    }
                    return (int) future.get();
                }catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
        }*/
        return -2;
    }
}
