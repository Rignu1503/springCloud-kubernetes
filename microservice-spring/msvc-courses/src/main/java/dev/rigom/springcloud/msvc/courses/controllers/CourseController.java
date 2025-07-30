package dev.rigom.springcloud.msvc.courses.controllers;

import dev.rigom.springcloud.msvc.courses.entity.Course;
import dev.rigom.springcloud.msvc.courses.service.ICourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class CourseController {

    @Autowired
    private final ICourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> findAll() {

        List<Course> courses = courseService.findAll();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable Long id) {

        return courseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> save(
            @Valid @RequestBody Course course, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {

            return validatedRequest(bindingResult);
        }

        Course savedCourse = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id, @RequestBody Course course, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return validatedRequest(bindingResult);
        }

        Optional<Course> existingCourse = courseService.findById(id);

        if (existingCourse.isPresent()) {
            Course updatedCourse = existingCourse.get();
            updatedCourse.setName(course.getName());

            return ResponseEntity.ok(courseService.save(updatedCourse));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        Optional<Course> course = courseService.findById(id);

        if (course.isPresent()) {
            courseService.deleteById(id);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    private static ResponseEntity<Map<String, String>> validatedRequest(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        bindingResult.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "error: " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
