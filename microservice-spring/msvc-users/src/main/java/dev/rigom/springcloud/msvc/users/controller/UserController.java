package dev.rigom.springcloud.msvc.users.controller;

import dev.rigom.springcloud.msvc.users.models.entity.User;
import dev.rigom.springcloud.msvc.users.service.IUSerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/users")
@AllArgsConstructor
public class UserController {

    @Autowired
    private final IUSerService userService;

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {

        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> existingUser = userService.findById(id);

        if (existingUser.isPresent()) {
            User userUpdate = existingUser.get();
            userUpdate.setName(user.getName());
            userUpdate.setEmail(user.getEmail());
            userUpdate.setPassword(user.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userUpdate));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
