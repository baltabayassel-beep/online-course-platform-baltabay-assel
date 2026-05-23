package kz.baltabay.assel.courseplatform.controller;

import jakarta.validation.Valid;
import java.util.List;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.LessonRequest;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.LessonResponse;
import kz.baltabay.assel.courseplatform.service.BaltabayAsselLearningService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BaltabayAsselLessonController {
    private final BaltabayAsselLearningService learningService;

    public BaltabayAsselLessonController(BaltabayAsselLearningService learningService) {
        this.learningService = learningService;
    }

    @GetMapping("/courses/{courseId}/lessons")
    public List<LessonResponse> findByCourse(@PathVariable Long courseId) {
        return learningService.findLessons(courseId);
    }

    @PostMapping("/courses/{courseId}/lessons")
    @ResponseStatus(HttpStatus.CREATED)
    public LessonResponse create(@PathVariable Long courseId, @Valid @RequestBody LessonRequest request) {
        return learningService.createLesson(courseId, request);
    }

    @PutMapping("/lessons/{id}")
    public LessonResponse update(@PathVariable Long id, @Valid @RequestBody LessonRequest request) {
        return learningService.updateLesson(id, request);
    }

    @DeleteMapping("/lessons/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        learningService.deleteLesson(id);
    }
}
