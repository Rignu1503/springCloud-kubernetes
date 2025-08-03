package dev.rigom.springcloud.msvc.users.controller;

import dev.rigom.springcloud.msvc.users.models.entity.User;
import dev.rigom.springcloud.msvc.users.service.IUSerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
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
    public ResponseEntity<?> save(
            @Valid @RequestBody User user, BindingResult bindingResult) {

        if(userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "Email already exists"));
        }

        if (bindingResult.hasErrors()) {
            return validatedRequest(bindingResult);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @Valid @PathVariable Long id, @RequestBody User user, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return validatedRequest(bindingResult);
        }

        Optional<User> existingUser = userService.findById(id);

        if (existingUser.isPresent()) {
            User userUpdate = existingUser.get();

            if(!user.getEmail().equalsIgnoreCase(userUpdate.getEmail()) && userService.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("error", "Email already exists"));
            }

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

    @GetMapping("/users-by-course")
    public ResponseEntity<?> findAllByIds(@RequestParam List<Long> ids) {

        return ResponseEntity.ok(userService.findAllByIds(ids));
    }


    private static ResponseEntity<Map<String, String>> validatedRequest(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        bindingResult.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "error: " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
