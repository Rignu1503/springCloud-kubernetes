package dev.rigom.springcloud.msvc.courses.service;

import dev.rigom.springcloud.msvc.courses.models.dto.User;
import dev.rigom.springcloud.msvc.courses.models.entity.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseService {

    List<Course> findAll();
    Optional<Course> findById(Long id);
    Course save(Course course);
    void deleteById(Long id);

    //Asignar un usuario un usuario existente
    Optional<User> assignUser(User user, Long courseId);
    //Asigniar un usuario nuevo a un curso
    Optional<User> AddUser(User user, Long courseId);
    //Desasignar un usuario de un curso
    Optional<User> removeUser(User user, Long courseId);
    //Buscar todos los usuarios de un curso
    Optional<Course> findByIdWithUsers(Long id);
    //Eliminar usuarios de un curso
    void deleteCourseByUserId(Long userId);

}
