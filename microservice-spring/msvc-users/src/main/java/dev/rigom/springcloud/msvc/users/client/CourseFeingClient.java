package dev.rigom.springcloud.msvc.users.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-courses", url = "http://localhost:8002")
public interface CourseFeingClient {

    @DeleteMapping("/courses/delete-user-course/{userId}")
    void deleteCourseByUserId(@PathVariable Long userId);
}
