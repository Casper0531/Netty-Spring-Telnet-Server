package com.github.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/*
 * 이벤트 핸들러를 전역변수로 생성 및 공유함으로써 매번 채널 생성될 때마다 객체를 생성하는 부하를 줄임 
 */
public class TelnetServerInitializer extends ChannelInitializer<SocketChannel> {
	// 네티가 제공하는 문자열 디코더 객체 생성 
	private static final StringDecoder DECODER = new StringDecoder();
	// 네티가 제공하는 문자열 인코더 객체 생
	private static final StringEncoder ENCODER = new StringEncoder();
	// 텔넷 서버의 실제 업무를 처리하기 위한 핸들러 객체 
	private static final TelnetServerHandler SERVER_HANDLER = new TelnetServerHandler();

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		// 구분자 기반 디코더 할당 및 채널 파이프라인 맨 앞에 등록
		// 데이터 최대 크기 8192 지정 및 줄바꿈문자를 기반으로 데이터 구분 
		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast(DECODER);
		pipeline.addLast(ENCODER);
		pipeline.addLast(SERVER_HANDLER);
	}
	
	

}
