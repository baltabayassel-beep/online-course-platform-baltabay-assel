package kz.baltabay.assel.courseplatform.controller;

import jakarta.validation.Valid;
import java.util.List;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.EnrollmentRequest;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.EnrollmentResponse;
import kz.baltabay.assel.courseplatform.service.BaltabayAsselLearningService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollments")
public class BaltabayAsselEnrollmentController {
    private final BaltabayAsselLearningService learningService;

    public BaltabayAsselEnrollmentController(BaltabayAsselLearningService learningService) {
        this.learningService = learningService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentResponse enroll(@Valid @RequestBody EnrollmentRequest request) {
        return learningService.enroll(request);
    }

    @GetMapping
    public List<EnrollmentResponse> findByStudent(@RequestParam Long studentId) {
        return learningService.findEnrollments(studentId);
    }
}
