package kz.baltabay.assel.courseplatform.controller;

import jakarta.validation.Valid;
import java.util.List;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.GradeSubmissionRequest;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.SubmissionRequest;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.SubmissionResponse;
import kz.baltabay.assel.courseplatform.service.BaltabayAsselLearningService;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/submissions")
public class BaltabayAsselSubmissionController {
    private final BaltabayAsselLearningService learningService;

    public BaltabayAsselSubmissionController(BaltabayAsselLearningService learningService) {
        this.learningService = learningService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubmissionResponse submit(@Valid @RequestBody SubmissionRequest request) {
        return learningService.submit(request);
    }

    @PutMapping("/{id}/grade")
    public SubmissionResponse grade(@PathVariable Long id, @Valid @RequestBody GradeSubmissionRequest request) {
        return learningService.grade(id, request);
    }

    @GetMapping
    public List<SubmissionResponse> findAll(@RequestParam(required = false) Long studentId,
                                            @RequestParam(required = false) Long assignmentId) {
        return learningService.findSubmissions(studentId, assignmentId);
    }
}
