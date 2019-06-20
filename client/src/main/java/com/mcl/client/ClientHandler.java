package com.mcl.client;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import com.mcl.pojo.Request;
import com.mcl.pojo.Response;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientHandler extends ChannelHandlerAdapter{

    long start = 0;
    long end = 0;

        @Override
        public void channelActive(ChannelHandlerContext ctx){
            start = System.currentTimeMillis()/1;
            System.out.println("--------ClientHandler channelActive---准备发送请求！-------");

            for(int i = 0; i<10000; i++){
                ctx.write(select());
            }
//            ctx.write(select());
            ctx.flush();
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg)
                throws Exception{
            System.out.println("--------channelRead---服务器发来的数据为：[" + msg.toString() + "]");
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx)
                throws Exception{
            System.out.println("----------------handler channelReadComplete ");
            ctx.flush();
            end = System.currentTimeMillis()/1;
            System.out.println(String.valueOf(end-start));
        }


        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
            System.out.println("--------------------------------------------------------------------------handler exceptionCaught");
            cause.printStackTrace();
            ctx.close();
        }

        public Request select(){
            Request req = new Request();
            int number = (int)(Math.random()*100)+1;
//            req.setType("vip");
            if ((number%3)==0){
                req.setType("ComBufImpl");
            }else if ((number%3)==1){
                req.setType("VIPBufImpl");
            }else {
                req.setType("ExpBufImpl");
            }
            return req;
        }
}
