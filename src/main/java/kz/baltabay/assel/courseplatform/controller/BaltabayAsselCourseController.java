package kz.baltabay.assel.courseplatform.controller;

import jakarta.validation.Valid;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselCourseDtos.CourseRequest;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselCourseDtos.CourseResponse;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselCourseLevel;
import kz.baltabay.assel.courseplatform.service.BaltabayAsselCourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class BaltabayAsselCourseController {
    private final BaltabayAsselCourseService courseService;

    public BaltabayAsselCourseController(BaltabayAsselCourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Page<CourseResponse> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BaltabayAsselCourseLevel level,
            @RequestParam(required = false) Boolean published,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        Sort sort = "asc".equalsIgnoreCase(direction) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return courseService.findAll(search, category, level, published, pageable);
    }

    @GetMapping("/{id}")
    public CourseResponse findById(@PathVariable Long id) {
        return courseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse create(@Valid @RequestBody CourseRequest request) {
        return courseService.create(request);
    }

    @PutMapping("/{id}")
    public CourseResponse update(@PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        return courseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }
}
