package kz.baltabay.assel.courseplatform.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselSubmissionStatus;

public class BaltabayAsselLearningDtos {
    public record LessonRequest(@NotBlank String title, @NotBlank String content, @NotNull Integer position) {
    }

    public record LessonResponse(Long id, String title, String content, Integer position, Long courseId) {
    }

    public record EnrollmentRequest(@NotNull Long studentId, @NotNull Long courseId) {
    }

    public record EnrollmentResponse(Long id, Long studentId, String studentName, Long courseId, String courseTitle,
                                     LocalDateTime enrolledAt, int progressPercent) {
    }

    public record AssignmentRequest(@NotBlank String title, @NotBlank String taskText, LocalDateTime dueDate,
                                    @NotNull Long courseId) {
    }

    public record AssignmentResponse(Long id, String title, String taskText, LocalDateTime dueDate, Long courseId) {
    }

    public record SubmissionRequest(@NotNull Long assignmentId, @NotNull Long studentId, @NotBlank String answerText) {
    }

    public record GradeSubmissionRequest(@NotNull @Min(0) @Max(100) Integer grade,
                                         @NotNull BaltabayAsselSubmissionStatus status) {
    }

    public record SubmissionResponse(Long id, Long assignmentId, Long studentId, String answerText, Integer grade,
                                     BaltabayAsselSubmissionStatus status, LocalDateTime submittedAt) {
    }
}
