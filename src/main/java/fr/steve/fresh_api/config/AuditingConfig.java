package fr.steve.fresh_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import fr.steve.fresh_api.model.entity.User;
import fr.steve.fresh_api.security.SpringSecurityAuditorAware;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

    @Bean
    public AuditorAware<User> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }
    
}
