package dev.rigom.springcloud.msvc.courses.clients;

import dev.rigom.springcloud.msvc.courses.models.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-users", url = "http://localhost:8001")
/*
 * Marcamos que microservicio es un cliente Feign y la dirección del servicio
 */
public interface UserFeingClient {

    // Aquí se pueden definir los métodos que interactúan con el microservicio msvc-users
    // Por ejemplo, para obtener un usuario por ID:
    // @GetMapping("/users/{id}")
    // User getUserById(@PathVariable Long id);

    //Se debe poener la misma ruta que el microservicio msvc-users, metodo HTTP y parámetros
    @GetMapping("/users/{id}")
    User findById(@PathVariable Long id);


    @PostMapping("/users")
    User save(@RequestBody User user);

    @GetMapping("/users/users-by-course")
    List<User> getUsersByCourse(@RequestParam Iterable<Long> ids);



}