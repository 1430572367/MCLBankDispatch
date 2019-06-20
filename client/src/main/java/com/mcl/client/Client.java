package com.mcl.client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.CyclicBarrier;

public class Client {

    private  int nPort ;
    private  String strHost ;
    private  CyclicBarrier cb = new CyclicBarrier(10);

    public Client(int nPort, String strHost) {
        this.nPort = nPort;
        this.strHost = strHost;
        try {
            System.out.println("---------------- connect Client");
           /* for (int i = 0; i < 10; i++) {
                new Thread(){
                    public void run(){
                        try {
                            new Client().connect(nPort, strHost);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }*/
            connect(nPort, strHost);
        } catch (Exception e) {
            System.out.println("----------------Client Error");
            e.printStackTrace();
        }
    }

    public void connect(int nPort, String strHost) throws Exception{
            EventLoopGroup group = new NioEventLoopGroup();
            try{
                Bootstrap b = new Bootstrap();
                b.group(group).channel(NioSocketChannel.class)
                        .option(ChannelOption.TCP_NODELAY,  true)
                        .handler(new ChannelInitializer<SocketChannel>(){
                            @Override
                            public void initChannel(SocketChannel ch) throws Exception{
                                ch.pipeline().addLast(
                                        new ObjectDecoder(1024, ClassResolvers
                                                .cacheDisabled(this.getClass().getClassLoader())));

                                ch.pipeline().addLast(new ObjectEncoder());
                                ch.pipeline().addLast(new ClientHandler());
                            }
                        });

                ChannelFuture f = b.connect(strHost,  nPort).sync();

//			if(f.isSuccess()){
//				System.out.println("----------------main  get channel");
//			}else{
//				System.out.println("----------------main  get channel ---f.channel().closeFuture().sync(); END!!!!");
//			}

                f.channel().closeFuture().sync();

//			f.channel().closeFuture();
            }finally{
                System.out.println("----------------main  get channel Error !!! ---------");
//			group.shutdownGracefully();
            }
        }
}
