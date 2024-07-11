package com.fc8.projectboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        // TODO : Spring Security 로 인증 기능을 붙이게 될 때, 수정해야 함
        return () -> Optional.of("reddyong");   // Jpa auditing 을 할 때 들어가는 사람 이름에 대한 임의로 들어가는 이름 설정
    }
}
