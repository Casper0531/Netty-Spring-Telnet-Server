package com.github.netty;

import java.net.InetAddress;
import java.util.Date;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {

	@Override
	/*
	 * 클라이언트 접속을 알리는 이벤
	 */
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.write("환영합니다. " + InetAddress.getLocalHost().getHostName() + "에 접속하셨습니다!\r\n");
		ctx.write("현재 시간은 " + new Date() + " 입니다.\r\n");
		ctx.flush();
	}

	@Override
	/*
	 * 데이터 수신 이벤트 
	 */
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		String response;
		boolean close = false;
		
		if (msg.isEmpty()) {
			response = "명령을 입력해 주세요.\r\n";
		} 
		else if ("bye".equals(msg.toLowerCase())) {
			response = "좋은 하루 되세요!\r\n";
			close = true;
		}
		else {
			response = "입력하신 명령이 '" + msg + "' 입니까?\r\n";
		}
		
		// 응답 메세지를 채널 버퍼에 할
		ChannelFuture future = ctx.write(response);
		
		// 입력된 메세지가 종료된다면 ChannelFuture 객체에 채널 종료 리스너를 할당 
		if(close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}

	}

	@Override
	/*
	 * 데이터 수신 완료 이벤트 
	 */
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
