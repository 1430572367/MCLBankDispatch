package com.mcl.dispatch;

import com.mcl.buffer.ComBufImpl;
import com.mcl.buffer.ExpBufImpl;
import com.mcl.buffer.IBuf;
import com.mcl.buffer.VIPBufImpl;
import com.mcl.common.AnotationBuf;
import com.mcl.dispatch.utils.Dom4jUtil;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

public final class Dispatch implements ApplicationContextAware {

    //方法bean暂时存在Map里头
    public static Map<String, Object> handlerMap = new HashMap<>();

    //双重检查锁
    private static Dispatch echoDispatch = null;
    private Dispatch(){}
    //生成调度器
    public static Dispatch getInstance(){
        if (null==echoDispatch){
            synchronized (Dispatch.class){
                if (null==echoDispatch){
                    echoDispatch=new Dispatch();
                }
            }
        }
        return echoDispatch;
    }

    /*private IEchoBuffer VIPEchoBuffer=null;
    private IEchoBuffer ExpEchoBuffer=null;
    private IEchoBuffer ComEchoBuffer=null;*/

    public IBuf getBuffer(String type){
        /*if ("VIP".equals(type)) {
//            if (VIPEchoBuffer!=null) return VIPEchoBuffer;
//            return VIPEchoBuffer = (IEchoBuffer) new Dom4jUtil().getBean("VIPEchoBufferImpl");
            return   VIPBufImpl.getInstance();
        }else if("Express".equals(type)){
//            if (ExpEchoBuffer!=null) return ExpEchoBuffer;
//            return ExpEchoBuffer = (IEchoBuffer) new Dom4jUtil().getBean("ExpEchoBufferImpl");
            return ExpBufImpl.getInstance();
        }else if("Common".equals(type)){
//            if (ComEchoBuffer!=null) return ComEchoBuffer;
//            return ComEchoBuffer = (IEchoBuffer) new Dom4jUtil().getBean("ComEchoBufferImpl");
            return   ComBufImpl.getInstance();
        }
        return null;*/

   return (IBuf) handlerMap.get(type);

    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(AnotationBuf.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(AnotationBuf.class).value().getName();
                System.out.println("Loading service: {}"+interfaceName);
                String[] name = interfaceName.split("\\.");
                String subName = name[name.length-1];
                handlerMap.put(subName, serviceBean);
                System.out.println(subName);
            }
        }
    }
}
