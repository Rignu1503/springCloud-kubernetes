package dev.rigom.springcloud.msvc.courses.service;

import dev.rigom.springcloud.msvc.courses.entity.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseService {

    List<Course> findAll();
    Optional<Course> findById(Long id);
    Course save(Course course);
    void deleteById(Long id);
}
