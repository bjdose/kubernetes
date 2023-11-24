package org.bjsalcedo.springcloud.svc.users.api.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bjsalcedo.springcloud.svc.users.dao.entities.User;
import org.bjsalcedo.springcloud.svc.users.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> listUsers() {
        return userService.listUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        return userService.byId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.error("User not found with id: {}", id);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND).build();
                });
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody User user) {
        return userService.byId(id)
                .map(u -> {
                    u.setName(user.getName());
                    u.setEmail(user.getEmail());
                    return ResponseEntity.ok(userService.save(u));
                })
                .orElseGet(() -> {
                    log.error("User not found with id: {}", id);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return userService.byId(id)
                .map(u -> {
                    userService.delete(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> {
                    log.error("User not found with id: {}", id);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND).build();
                });
    }
}
