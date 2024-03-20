package fr.steve.fresh_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Deprecated
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

  @Override
  public void configureMessageBroker(@NonNull MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic");
    config.setApplicationDestinationPrefixes("/app");
  }

  @Override
  public void registerStompEndpoints(@NonNull StompEndpointRegistry registry) {
    registry.addEndpoint("/ws").setAllowedOriginPatterns(this.allowedOrigins.split(","));
  }

}
