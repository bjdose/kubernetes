package org.bjsalcedo.springcloud.svc.courses.api.controllers;

import feign.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bjsalcedo.springcloud.svc.courses.dao.entities.Course;
import org.bjsalcedo.springcloud.svc.courses.dao.models.User;
import org.bjsalcedo.springcloud.svc.courses.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
@Slf4j
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public List<Course> listCourses() {
        return courseService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        return courseService.byIdWithUsers(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.error("Course not found with id: {}", id);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND).build();
                });
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Course course, BindingResult result) {
        if (result.hasErrors()) return validate(result);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(courseService.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) return validate(result);

        return courseService.byId(id)
                .map(u -> {
                    u.setName(course.getName());
                    return ResponseEntity.ok(courseService.save(u));
                })
                .orElseGet(() -> {
                    log.error("Course not found with id: {}", id);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return courseService.byId(id)
                .map(u -> {
                    courseService.delete(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> {
                    log.error("Course not found with id: {}", id);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteCourseUserById(@PathVariable Long userId) {
        courseService.deleteCourseUserById(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{courseId}/assign")
    public ResponseEntity<?> assign(@RequestBody User user, @PathVariable Long courseId) {
        return courseService.assign(courseId, user.getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.error("Course not found with id: {}", courseId);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND).build();
                });

    }

    @PostMapping("/{courseId}/create")
    public ResponseEntity<?> create(@RequestBody User user, @PathVariable Long courseId) {
        return courseService.create(courseId, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.error("Course not found with id: {}", courseId);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND).build();
                });
    }

    @DeleteMapping("/{courseId}/remove")
    public ResponseEntity<?> remove(@RequestBody User user, @PathVariable Long courseId) {
        return courseService.remove(courseId, user.getId())
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.error("Course not found with id: {}", courseId);
                    return ResponseEntity
                            .status(HttpStatus.NOT_FOUND).build();
                });
    }

    private static ResponseEntity<List<ObjectError>> validate(BindingResult result) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(result.getAllErrors());
    }
}
