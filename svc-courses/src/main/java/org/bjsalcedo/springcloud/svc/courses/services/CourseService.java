package org.bjsalcedo.springcloud.svc.courses.services;

import lombok.RequiredArgsConstructor;
import org.bjsalcedo.springcloud.svc.courses.dao.entities.Course;
import org.bjsalcedo.springcloud.svc.courses.dao.repositories.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

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

}
