package fr.steve.fresh_api.controller;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.steve.fresh_api.dto.CreateUserDto;
import fr.steve.fresh_api.dto.LoginDto;
import fr.steve.fresh_api.model.entity.User;
import fr.steve.fresh_api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authManager;

    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    @PostMapping("auth/register")
    public ResponseEntity<User> register(@RequestBody CreateUserDto dto) {
        dto.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        return ResponseEntity.ok(this.userService.create(dto));
    }

    @PostMapping("auth/login")
    public ResponseEntity<Void> login(@RequestBody LoginDto dto, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                dto.getEmail(), dto.getPassword());
        Authentication authentication = authManager.authenticate(token);
        User user = (User) authentication.getPrincipal();
        user.setLastLoginAt(Instant.now());
        this.userService.save(user);
        SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("auth/me")
    public ResponseEntity<User> me(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(user);
    }

}
