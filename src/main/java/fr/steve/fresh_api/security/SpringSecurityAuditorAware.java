package fr.steve.fresh_api.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.steve.fresh_api.model.entity.User;

public class SpringSecurityAuditorAware implements AuditorAware<User> {
    @Override
    public Optional<User> getCurrentAuditor() {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            return Optional.ofNullable((User) authentication.getPrincipal());
        }

        return Optional.empty();
    }
}
