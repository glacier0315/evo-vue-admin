package com.glacier.config;

import com.glacier.common.security.properties.JwtProperties;
import com.glacier.common.security.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * date 2021-07-14 17:50
 *
 * @author glacier
 * @version 1.0
 */
@Configuration
public class PropertiesAutoConfiguration {
    
    /**
     * @return 安全配置实例
     */
    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix = "settings.security")
    SecurityProperties securityProperties() {
        return new SecurityProperties();
    }
    
    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties(prefix = "settings.jwt")
    JwtProperties JwtProperties() {
        return new JwtProperties();
    }
}
