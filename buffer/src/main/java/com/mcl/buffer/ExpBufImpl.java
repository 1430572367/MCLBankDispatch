package com.mcl.buffer;

import com.mcl.common.AnotationBuf;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
@AnotationBuf(IBuf.class) // 指定远程接口
public class ExpBufImpl implements IBuf {
    private AtomicInteger atomicInteger = new AtomicInteger(1);
    private ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();

    //双重检查锁
    private volatile static ExpBufImpl expEchoBuffer = null;
    private ExpBufImpl(){}
    //生成调度器
    public static ExpBufImpl getInstance(){
        if (null==expEchoBuffer){
            synchronized (ExpBufImpl.class){
                if (null==expEchoBuffer){
                    expEchoBuffer=new ExpBufImpl();
                }
            }
        }
        return expEchoBuffer;
    }

    public void proNum(){
        int num = atomicInteger.getAndIncrement();
        concurrentLinkedQueue.offer(num);
    }
    public int conNum(){
        while (concurrentLinkedQueue.isEmpty()){
            System.out.println("请稍等1秒钟！");
            /*try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        return (int) concurrentLinkedQueue.poll();
    }
}
