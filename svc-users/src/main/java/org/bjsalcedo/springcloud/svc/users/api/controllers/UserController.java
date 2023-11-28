package org.bjsalcedo.springcloud.svc.users.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bjsalcedo.springcloud.svc.users.dao.entities.User;
import org.bjsalcedo.springcloud.svc.users.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> listUsers() {
        return userService.list();
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
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) return validate(result);

        if (userService.byEmail(user.getEmail()).isPresent()) {
            result.addError(new ObjectError("email", "Email already exists"));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) return validate(result);
        
        return userService.byId(id)
                .map(u -> {
                    if (!user.getEmail().equalsIgnoreCase(u.getEmail()) &&
                            userService.byEmail(user.getEmail()).isPresent()) {
                        result.addError(new ObjectError("email", "Email already exists"));
                    }

                    if (result.hasErrors()) return validate(result);

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

    @GetMapping("/courses")
    public ResponseEntity<?> getUsersByCourseIds(@RequestParam List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.badRequest().body("Course IDs are required.");
        }
        List<User> users = userService.byIds(ids);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    private static ResponseEntity<List<ObjectError>> validate(BindingResult result) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(result.getAllErrors());
    }
}
