package org.bjsalcedo.springcloud.svc.courses.dao.repositories;

import org.bjsalcedo.springcloud.svc.courses.dao.entities.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
