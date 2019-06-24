package com.github.netty.spring;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/*
 * Configuration : 지정된 클래스가 스프링의 설정 정보를 포함한 클래스임을 표시(Bean설정, ComponentScan, PropertySource)
 * ComponentScan : Spring의 컨텍스트가 해당 클래스를 동적으로 찾을 수 있도록 한다는 의미. 입력 패키지 대상으로 하위까지 검색
 * PropertySource : 설정정보를 가진 파일의 위치에서 파일을 읽어서 Environment객체로 자동 저장.classpath에서 설정 파일을 검색
 */
@Configuration
@ComponentScan("com.github.netty.spring")
@PropertySource("classpath:netty-telnet-server.properties")
public class TelnetServerConfig {
	// 설정파일(netty-telnet-server.properties)에서 value의 키의 설정값을 받아 변수에 할당  
	@Value("${boss.thread.count}")
	private int bossCount;
	
	@Value("${worker.thread.count}")
	private int workerCount;
	
	@Value("${tcp.port}")
	private int tcpPort;
	
	public int getBossCount() {
		return bossCount;
	}
	
	public int getWorkerCount() {
		return workerCount;
	}
	
	public int getTcpPort() {
		return tcpPort;
	}
	
	// 다른 Bean에서 tcpSocketAddress라는 이름으로 사용 가능 
	@Bean(name="tcpSocketAddress")
	public InetSocketAddress tcpPort() {
		return new InetSocketAddress(tcpPort);
	}
	
	// PropertySource어노테이션에 사용할 Environment객체를 생성하는 Bean을 생성 
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
