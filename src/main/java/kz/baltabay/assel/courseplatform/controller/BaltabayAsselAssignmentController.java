package kz.baltabay.assel.courseplatform.controller;

import jakarta.validation.Valid;
import java.util.List;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.AssignmentRequest;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.AssignmentResponse;
import kz.baltabay.assel.courseplatform.service.BaltabayAsselLearningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class BaltabayAsselAssignmentController {
    private final BaltabayAsselLearningService learningService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentResponse create(@Valid @RequestBody AssignmentRequest request) {
        return learningService.createAssignment(request);
    }

    @GetMapping
    public List<AssignmentResponse> findByCourse(@RequestParam Long courseId) {
        return learningService.findAssignments(courseId);
    }
}
