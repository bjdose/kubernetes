package org.bjsalcedo.springcloud.svc.users.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "svc-courses", url = "localhost:8002")
public interface CourseClientRest {

    @DeleteMapping("api/courses/delete-user/{userId}")
    void deleteCourseUserById(@PathVariable Long userId);
}
