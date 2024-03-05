package fr.steve.fresh_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.steve.fresh_api.model.entity.User;
import fr.steve.fresh_api.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> list() {
        return ResponseEntity.ok(this.userService.getAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> get(@PathVariable(value = "id") @NonNull Long id) {
        return ResponseEntity.ok(this.userService.get(id));
    }
}
