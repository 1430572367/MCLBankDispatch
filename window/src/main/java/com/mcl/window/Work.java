package com.mcl.window;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import com.mcl.dispatch.Dispatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Work implements Callable{

    private static Log log = LogFactory.getLog(Work.class);

    private CyclicBarrier cyclicBarrier;
    private AtomicInteger count;
    private String type;


    public Work(CyclicBarrier cyclicBarrier, AtomicInteger atomicInteger, String type) {
        this.cyclicBarrier = cyclicBarrier;
        this.count = atomicInteger;
        this.type = type;
    }

    @Override
    public Object call() throws Exception {
        while (true){
            int num = Dispatch.getInstance().getBuffer(type).conNum();
//            if(count.addAndGet(1)%10==0){
//                cyclicBarrier.await();
                log.info(type+" 人数："+count.getAndIncrement());
//            }
            return num;
        }
    }
}
