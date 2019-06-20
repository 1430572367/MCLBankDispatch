package com.mcl.buffer;

import com.mcl.common.AnotationBuf;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@AnotationBuf(IBuf.class) // 指定远程接口
public class VIPBufImpl implements IBuf {

    final public AtomicInteger atomicInteger = new AtomicInteger(1);
    final public ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();

    //双重检查锁
    private volatile static VIPBufImpl vipEchoBuffer = null;
    private VIPBufImpl(){}
    //生成调度器
    public static VIPBufImpl getInstance(){
        if (null==vipEchoBuffer){
            synchronized (VIPBufImpl.class){
                if (null==vipEchoBuffer){
                    vipEchoBuffer=new VIPBufImpl();
                }
            }
        }
        return vipEchoBuffer;
    }

    @Override
    public void proNum(){
        int num = atomicInteger.getAndIncrement();
        concurrentLinkedQueue.offer(num);
    }
    @Override
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
