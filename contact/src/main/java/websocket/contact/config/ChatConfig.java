package websocket.contact.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")                // stomp 연결 주소
                .withSockJS();                                  // 개발 서버의 접속 주소는 "ws://localhost:8080
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");     // 메시지를 발행하는 요청의 prefix
        config.setApplicationDestinationPrefixes("/pub");       // 메시지를 구독하는 요청의 prefix
    }
}
