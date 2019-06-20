package com.mcl.buffer;

import com.mcl.common.AnotationBuf;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现服务接口
 */
@AnotationBuf(IBuf.class) // 指定远程接口
public class ComBufImpl implements IBuf {
    private AtomicInteger atomicInteger = new AtomicInteger(1);
    private ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();

    //双重检查锁
    private volatile static ComBufImpl comEchoBuffer = null;
    private ComBufImpl(){}
    //生成调度器
    public static ComBufImpl getInstance(){
        if (null==comEchoBuffer){
            synchronized (ComBufImpl.class){
                if (null==comEchoBuffer){
                    comEchoBuffer=new ComBufImpl();
                }
            }
        }
        return comEchoBuffer;
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
