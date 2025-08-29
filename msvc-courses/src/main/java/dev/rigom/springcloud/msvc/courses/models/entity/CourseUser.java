package dev.rigom.springcloud.msvc.courses.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CourseUser {
    /*
     * esta clase representa la relación entre un curso y un usuario
     * es una entidad que se utiliza para almacenar la información
     * de los usuarios que están inscritos en un curso
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true)
    private Long userId;

    //Se modifica el equals para que compare solo el id del usuario
    @Override
    public boolean equals(Object obj) {
       if (this == obj) {
           return true;
       }
       if (!(obj instanceof CourseUser)) {
           return false;
       }
         CourseUser other = (CourseUser) obj;
       return this.userId != null && this.userId.equals(other.userId);
    }
}
