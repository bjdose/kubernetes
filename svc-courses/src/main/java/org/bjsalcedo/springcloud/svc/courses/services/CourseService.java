package org.bjsalcedo.springcloud.svc.courses.services;

import lombok.RequiredArgsConstructor;
import org.bjsalcedo.springcloud.svc.courses.clients.UserClientRest;
import org.bjsalcedo.springcloud.svc.courses.dao.entities.Course;
import org.bjsalcedo.springcloud.svc.courses.dao.entities.CourseUser;
import org.bjsalcedo.springcloud.svc.courses.dao.models.User;
import org.bjsalcedo.springcloud.svc.courses.dao.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserClientRest userClientRest;

    @Transactional(readOnly = true)
    public List<Course> list() {
        return (List<Course>) courseRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Optional<Course> byId(Long id) {
        return courseRepository.findById(id);
    }
    @Transactional
    public Course save(Course user) {
        return courseRepository.save(user);
    }
    @Transactional
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    @Transactional
    public Optional<User> assign(Long courseId, Long userId) {
        return courseRepository.findById(courseId)
                .map(course -> addUserToCourse(course, userClientRest.details(userId)));
    }

    @Transactional
    public Optional<User> create(Long courseId, User user) {
        return courseRepository.findById(courseId)
                .map(course -> addUserToCourse(course, userClientRest.create(user)));
    }

    @Transactional
    public Optional<User> remove(Long courseId, Long userId) {
        return courseRepository.findById(courseId)
                .map(course -> {
                    User user = userClientRest.details(userId);
                    return removeUserFromCourse(course, user) ? user : null;
                });
    }

    private User addUserToCourse(Course course, User user) {
        if (user == null) {
            return null;
        }
        CourseUser courseUser = new CourseUser();
        courseUser.setUserId(user.getId());

        if (course.getCourseUsers() == null) {
            course.setCourseUsers(new ArrayList<>());
        }
        course.getCourseUsers().add(courseUser);
        courseRepository.save(course);
        return user;
    }

    private boolean removeUserFromCourse(Course course, User user) {
        if (user == null || course.getCourseUsers() == null) {
            return false;
        }
        boolean removed = course.getCourseUsers().removeIf(u -> u.getUserId().equals(user.getId()));
        courseRepository.save(course);
        return removed;
    }

}
