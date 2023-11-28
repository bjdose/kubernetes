package org.bjsalcedo.springcloud.svc.courses.clients;

import jakarta.validation.Valid;
import org.bjsalcedo.springcloud.svc.courses.dao.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "svc-users", url = "localhost:8001")
public interface UserClientRest {
    @GetMapping("/api/users/{id}")
    User details(@PathVariable Long id);

    @PostMapping("/api/users")
    User create(@RequestBody @Valid User user);

    @GetMapping("/api/users/courses")
    List<User> getStudentsByCourse(@RequestParam Iterable<Long> ids);
}
