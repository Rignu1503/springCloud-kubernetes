package dev.rigom.springcloud.msvc.courses.repositories;

import dev.rigom.springcloud.msvc.courses.models.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Modifying
    @Query("delete from CourseUser c where c.userId = ?1")
    int deleteCourseByUserId(Long userId);  // Devuelve filas afectadas
}
