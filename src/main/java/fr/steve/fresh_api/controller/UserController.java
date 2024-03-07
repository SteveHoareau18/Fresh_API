package fr.steve.fresh_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fr.steve.fresh_api.dto.UpdateUserDto;
import fr.steve.fresh_api.model.entity.User;
import fr.steve.fresh_api.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> list() {
        List<User> users = this.userService.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> get(@PathVariable(value = "id") @NonNull Long id) {
        return ResponseEntity.ok(this.userService.get(id));
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody UpdateUserDto user,
            Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();

        if (id == authUser.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Vous ne pouvez pas modifier votre compte depuis cette interface");
        }

        return ResponseEntity.ok(this.userService.update(id, user));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        User authUser = (User) authentication.getPrincipal();

        if (id == authUser.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Vous ne pouvez pas supprimer votre compte depuis cette interface");
        }

        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
