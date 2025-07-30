package dev.rigom.springcloud.msvc.courses.repositories;

import dev.rigom.springcloud.msvc.courses.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
