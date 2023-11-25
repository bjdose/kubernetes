package org.bjsalcedo.springcloud.svc.courses.dao.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id", unique = true)
    Long userId;
}
