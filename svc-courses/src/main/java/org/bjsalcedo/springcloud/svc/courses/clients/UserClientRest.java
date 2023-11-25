package org.bjsalcedo.springcloud.svc.courses.clients;

import jakarta.validation.Valid;
import org.bjsalcedo.springcloud.svc.courses.dao.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "svc-users", url = "localhost:8001")
public interface UserClientRest {
    @GetMapping("/api/users/{id}")
    User details(@PathVariable Long id);

    @PostMapping("/api/users")
    User create(@RequestBody @Valid User user);
}
