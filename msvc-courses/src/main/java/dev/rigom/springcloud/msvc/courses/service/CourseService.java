package dev.rigom.springcloud.msvc.courses.service;

import dev.rigom.springcloud.msvc.courses.clients.UserFeingClient;
import dev.rigom.springcloud.msvc.courses.models.dto.User;
import dev.rigom.springcloud.msvc.courses.models.entity.Course;
import dev.rigom.springcloud.msvc.courses.models.entity.CourseUser;
import dev.rigom.springcloud.msvc.courses.repositories.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService implements ICourseService{

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final UserFeingClient userFeingClient;

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> assignUser(User user, Long courseId) {

        // Primero, buscamos el curso por su ID
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()) {
            //Con el cliente buscamos el usuario por su ID
            User existingUser  = userFeingClient.findById(user.getId());

            Course courseUser = course.get();
            CourseUser courseUserEntity = new CourseUser();
            courseUserEntity.setUserId(existingUser.getId());

            courseUser.addCourseUser(courseUserEntity);
            // Guardamos el curso actualizado con el usuario asignado
            courseRepository.save(courseUser);

            // Retornamos el usuario que fue asignado al curso
            return Optional.of(existingUser);
        }
        // Si el curso no existe, retornamos un Optional vacío
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> AddUser(User user, Long courseId) {

        // Primero, buscamos el curso por su ID
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()) {
            //Con el cliente creamos el usuario por su ID
            User saveUser = userFeingClient.save(user);

            Course courseUser = course.get();
            CourseUser courseUserEntity = new CourseUser();
            courseUserEntity.setUserId(saveUser.getId());

            courseUser.addCourseUser(courseUserEntity);
            // Guardamos el curso actualizado con el usuario asignado
            courseRepository.save(courseUser);

            // Retornamos el usuario que fue asignado al curso
            return Optional.of(saveUser);
        }
        // Si el curso no existe, retornamos un Optional vacío
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> removeUser(User user, Long courseId) {

        // Primero, buscamos el curso por su ID
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()) {
            //Con el cliente buscamos el usuario por su ID
            User existingUser  = userFeingClient.findById(user.getId());

            Course courseUser = course.get();
            CourseUser courseUserEntity = new CourseUser();
            courseUserEntity.setUserId(existingUser.getId());

            courseUser.removeCourseUser(courseUserEntity);
            //
            courseRepository.save(courseUser);

            // Retornamos el usuario que fue asignado al curso
            return Optional.of(existingUser);
        }
        // Si el curso no existe, retornamos un Optional vacío
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findByIdWithUsers(Long id) {

        Optional<Course> existingCourse = courseRepository.findById(id);
        if(existingCourse.isPresent()){
            Course course = existingCourse.get();

            if(!(course.getCourseUsers().isEmpty())){
                List<Long> userIds = course.getCourseUsers()
                        .stream()
                        .map(CourseUser::getUserId)
                        .toList();

                List<User> users = userFeingClient.getUsersByCourse(userIds);

                course.setUsers(users);
            }
            return Optional.of(course);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteCourseByUserId(Long userId) {
        courseRepository.deleteCourseByUserId(userId);
    }
}
