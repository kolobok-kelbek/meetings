package com.mittings.configuration;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {
  @Bean
  WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
    return tomcatServletWebServerFactory ->
        tomcatServletWebServerFactory.addContextCustomizers(
            (TomcatContextCustomizer)
                context -> context.setCookieProcessor(new LegacyCookieProcessor()));
  }
}
