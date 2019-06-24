package com.github.netty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

// 지정된 클래스의 객체가 스프링 컨텍스트에 등록되도록 한다 
@Component
public final class SpringTelnetServer {
	/*
	 * Autowired : 지정된 필드값이 자동 할당되도록 지정. 스프링 컨텍스트에 포함 된 Bean중에서 지정된 클래스와 동일한객체있을 때 할당 
	 */
	@Autowired
	@Qualifier("tcpSocketAddress")
	private int port;
	
	public void start(){
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new TelnetServerInitializer());
			
			ChannelFuture future = b.bind(port).sync();
			future.channel().closeFuture().sync();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}

	}

}
