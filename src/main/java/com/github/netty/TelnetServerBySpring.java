package com.github.netty;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.github.netty.spring.TelnetServerConfig;

public class TelnetServerBySpring {

	public static void main(String[] args) {
		AbstractApplicationContext springContext = null;
		try {
			// Annotation기반의 Spring context객체 생성. 인자로 입력되는 클래스는 콘텍스트를 생성하는데 필요한 설정정보가 포함 
			springContext = new AnnotationConfigApplicationContext(TelnetServerConfig.class);
			springContext.registerShutdownHook();
			
			// 설정정보가 저장된 컨텍스트를 통해 텔넷서버 클래스 객체를 가져온 후 실행 
			SpringTelnetServer server = springContext.getBean(SpringTelnetServer.class);
			server.start();
		}
		finally {
			springContext.close();
		}
		
	}

}
