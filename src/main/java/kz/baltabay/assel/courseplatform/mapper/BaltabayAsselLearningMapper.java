package kz.baltabay.assel.courseplatform.mapper;

import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.AssignmentResponse;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.EnrollmentResponse;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.LessonResponse;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.SubmissionResponse;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselAssignment;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselEnrollment;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselLesson;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselSubmission;
import org.springframework.stereotype.Component;

@Component
public class BaltabayAsselLearningMapper {
    public LessonResponse toLessonDto(BaltabayAsselLesson lesson) {
        return new LessonResponse(lesson.getId(), lesson.getTitle(), lesson.getContent(), lesson.getPosition(),
                lesson.getCourse().getId());
    }

    public EnrollmentResponse toEnrollmentDto(BaltabayAsselEnrollment enrollment) {
        return new EnrollmentResponse(enrollment.getId(), enrollment.getStudent().getId(),
                enrollment.getStudent().getFullName(), enrollment.getCourse().getId(), enrollment.getCourse().getTitle(),
                enrollment.getEnrolledAt(), enrollment.getProgressPercent());
    }

    public AssignmentResponse toAssignmentDto(BaltabayAsselAssignment assignment) {
        return new AssignmentResponse(assignment.getId(), assignment.getTitle(), assignment.getTaskText(),
                assignment.getDueDate(), assignment.getCourse().getId());
    }

    public SubmissionResponse toSubmissionDto(BaltabayAsselSubmission submission) {
        return new SubmissionResponse(submission.getId(), submission.getAssignment().getId(),
                submission.getStudent().getId(), submission.getAnswerText(), submission.getGrade(),
                submission.getStatus(), submission.getSubmittedAt());
    }
}
