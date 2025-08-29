package dev.rigom.springcloud.msvc.courses.models.entity;

import dev.rigom.springcloud.msvc.courses.models.dto.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private List<CourseUser> courseUsers;

    @Transient // este campo no se persistirá en la base de datos
    private List<User> users;



    public void addCourseUser(CourseUser courseUser) {

        courseUsers.add(courseUser);
    }

    public void removeCourseUser(CourseUser courseUser) {

        courseUsers.remove(courseUser);
    }
}
