package com.mcl.server;

import com.mcl.pojo.Request;
import com.mcl.pojo.Response;
import com.mcl.window.ComWin;
import com.mcl.window.ExpWin;
import com.mcl.window.VIPWin;
import com.mcl.window.Work;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import com.mcl.dispatch.Dispatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.Socket;

public class ServerHandler extends ChannelHandlerAdapter {

    private static Log log = LogFactory.getLog(Work.class);
    long start = 0;
    long end = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        start = System.currentTimeMillis()/1;
        System.out.println("--------------------------------ServerHandler channelActive------------");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception{
        Request req = (Request)msg;   // 订购内容
        System.out.println("接收到客户端的数据: [  " + req.toString() + "   ]");
        String type = req.getType();
        ctx.writeAndFlush(select(type));	// 反馈消息
    }

    @Override
    public  void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        System.out.println("---------------exceptionCaught 网络异常，关闭网络");
        cause.printStackTrace();
        ctx.close();
    }

    public /*synchronized*/ Response select(String type){

         int num = 0;
         Dispatch ed = Dispatch.getInstance();

        end = System.currentTimeMillis()/1;

        if ("VIPBufImpl".equals(type)){
            ed.getBuffer(type).proNum();
            System.out.println("有"+type+"客户排队！");
            num = VIPWin.getInstance().handle();
            System.out.println(num+" 号 "+type+" 取号成功！"+String.valueOf(end-start)+"秒");
//            log.info(num+" 号 vip 取号成功！"+String.valueOf(end-start)+"秒");
        }else if("ComBufImpl".equals(type)){
            ed.getBuffer("ComBufImpl").proNum();
            System.out.println("有com客户排队！");
            num = ComWin.getInstance().handle();
            System.out.println(num+" 号 "+type+" 取号成功！"+String.valueOf(end-start)+"秒");
//            log.info(num+" 号 com 取号成功！"+String.valueOf(end-start)+"秒");
        }else if("exp".equals(type)){
            ed.getBuffer("Express").proNum();
            System.out.println("有exp客户排队！");
            num = ExpWin.getInstance().handle();
            System.out.println(num+" 号 "+type+" 取号成功！"+String.valueOf(end-start)+"秒");
//            log.info(num+" 号 exp 取号成功！"+String.valueOf(end-start)+"秒");
        }

        Response resp = new Response();
        resp.setNum(num);
        resp.setType(type);
        resp.setStrDesc("-------您的号码是"+num+"号，请到"+type+"窗口排队！");
        return resp;
    }

}
