package com.mcl.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
 
public class Server {

	private int nPort;

	public Server(int nPort) {
		this.nPort = nPort;
		System.out.println("---------------main start Server");
		try {
			bind(nPort);
		} catch (Exception e) {
			System.out.println("---------------main Error");
			e.printStackTrace();
		}
	}

    public void bind(int nPort) throws Exception {
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
				
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 100)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>(){
				@Override
				public void initChannel(SocketChannel ch) throws Exception{
					ch.pipeline()
					.addLast(new ObjectDecoder(1024*1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
					.addLast(new ObjectEncoder())
					.addLast(new ServerHandler());
				}
			});
			
			ChannelFuture f = b.bind(nPort).sync();
			System.out.println("---------------wait for Client connect");
			f.channel().closeFuture().sync();
		}finally {
			System.out.println("---------------wait for connect  Error!");
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	/*public static void main(String[] args){
		int nPort = 5656;
		nPort = Integer.valueOf(nPort);
		System.out.println("---------------main start Server");
		try {
			new Server().bind(nPort);
		} catch (Exception e) {
			System.out.println("---------------main Error");
			e.printStackTrace();
		}
	}*/
}
