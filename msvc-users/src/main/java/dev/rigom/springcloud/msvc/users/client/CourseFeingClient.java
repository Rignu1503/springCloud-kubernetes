package dev.rigom.springcloud.msvc.users.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-courses")
public interface CourseFeingClient {

    @DeleteMapping("/courses/delete-user-course/{userId}")
    void deleteCourseByUserId(@PathVariable Long userId);
}
