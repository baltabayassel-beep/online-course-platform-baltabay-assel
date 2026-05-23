package kz.baltabay.assel.courseplatform.service;

import java.util.List;
import kz.baltabay.assel.courseplatform.async.BaltabayAsselAsyncNotificationService;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.AssignmentRequest;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.AssignmentResponse;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.EnrollmentRequest;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.EnrollmentResponse;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.GradeSubmissionRequest;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.LessonRequest;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.LessonResponse;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.SubmissionRequest;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselLearningDtos.SubmissionResponse;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselAssignment;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselCourse;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselEnrollment;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselLesson;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselSubmission;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselUser;
import kz.baltabay.assel.courseplatform.exception.BaltabayAsselBadRequestException;
import kz.baltabay.assel.courseplatform.exception.BaltabayAsselNotFoundException;
import kz.baltabay.assel.courseplatform.mapper.BaltabayAsselLearningMapper;
import kz.baltabay.assel.courseplatform.repository.BaltabayAsselAssignmentRepository;
import kz.baltabay.assel.courseplatform.repository.BaltabayAsselEnrollmentRepository;
import kz.baltabay.assel.courseplatform.repository.BaltabayAsselLessonRepository;
import kz.baltabay.assel.courseplatform.repository.BaltabayAsselSubmissionRepository;
import kz.baltabay.assel.courseplatform.repository.BaltabayAsselUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BaltabayAsselLearningService {
    private static final Logger log = LoggerFactory.getLogger(BaltabayAsselLearningService.class);

    private final BaltabayAsselCourseService courseService;
    private final BaltabayAsselUserRepository userRepository;
    private final BaltabayAsselLessonRepository lessonRepository;
    private final BaltabayAsselEnrollmentRepository enrollmentRepository;
    private final BaltabayAsselAssignmentRepository assignmentRepository;
    private final BaltabayAsselSubmissionRepository submissionRepository;
    private final BaltabayAsselLearningMapper mapper;
    private final BaltabayAsselAsyncNotificationService notificationService;

    public BaltabayAsselLearningService(BaltabayAsselCourseService courseService,
                                        BaltabayAsselUserRepository userRepository,
                                        BaltabayAsselLessonRepository lessonRepository,
                                        BaltabayAsselEnrollmentRepository enrollmentRepository,
                                        BaltabayAsselAssignmentRepository assignmentRepository,
                                        BaltabayAsselSubmissionRepository submissionRepository,
                                        BaltabayAsselLearningMapper mapper,
                                        BaltabayAsselAsyncNotificationService notificationService) {
        this.courseService = courseService;
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.assignmentRepository = assignmentRepository;
        this.submissionRepository = submissionRepository;
        this.mapper = mapper;
        this.notificationService = notificationService;
    }

    public LessonResponse createLesson(Long courseId, LessonRequest request) {
        BaltabayAsselLesson lesson = new BaltabayAsselLesson();
        lesson.setCourse(courseService.getCourse(courseId));
        lesson.setTitle(request.title());
        lesson.setContent(request.content());
        lesson.setPosition(request.position());
        return mapper.toLessonDto(lessonRepository.save(lesson));
    }

    public List<LessonResponse> findLessons(Long courseId) {
        return lessonRepository.findByCourseIdOrderByPositionAsc(courseId).stream().map(mapper::toLessonDto).toList();
    }

    public LessonResponse updateLesson(Long id, LessonRequest request) {
        BaltabayAsselLesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new BaltabayAsselNotFoundException("Lesson not found"));
        lesson.setTitle(request.title());
        lesson.setContent(request.content());
        lesson.setPosition(request.position());
        return mapper.toLessonDto(lessonRepository.save(lesson));
    }

    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    public EnrollmentResponse enroll(EnrollmentRequest request) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(request.studentId(), request.courseId())) {
            throw new BaltabayAsselBadRequestException("Student already enrolled");
        }
        BaltabayAsselUser student = getUser(request.studentId());
        BaltabayAsselCourse course = courseService.getCourse(request.courseId());
        BaltabayAsselEnrollment enrollment = new BaltabayAsselEnrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        BaltabayAsselEnrollment saved = enrollmentRepository.save(enrollment);
        notificationService.sendEnrollmentNotification(student.getEmail(), course.getTitle());
        return mapper.toEnrollmentDto(saved);
    }

    public List<EnrollmentResponse> findEnrollments(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream().map(mapper::toEnrollmentDto).toList();
    }

    public AssignmentResponse createAssignment(AssignmentRequest request) {
        BaltabayAsselAssignment assignment = new BaltabayAsselAssignment();
        assignment.setCourse(courseService.getCourse(request.courseId()));
        assignment.setTitle(request.title());
        assignment.setTaskText(request.taskText());
        assignment.setDueDate(request.dueDate());
        return mapper.toAssignmentDto(assignmentRepository.save(assignment));
    }

    public List<AssignmentResponse> findAssignments(Long courseId) {
        return assignmentRepository.findByCourseId(courseId).stream().map(mapper::toAssignmentDto).toList();
    }

    public SubmissionResponse submit(SubmissionRequest request) {
        BaltabayAsselSubmission submission = new BaltabayAsselSubmission();
        submission.setAssignment(getAssignment(request.assignmentId()));
        submission.setStudent(getUser(request.studentId()));
        submission.setAnswerText(request.answerText());
        BaltabayAsselSubmission saved = submissionRepository.save(submission);
        notificationService.generateSubmissionReport(saved.getId());
        log.info("Submission created id={}", saved.getId());
        return mapper.toSubmissionDto(saved);
    }

    public SubmissionResponse grade(Long id, GradeSubmissionRequest request) {
        BaltabayAsselSubmission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new BaltabayAsselNotFoundException("Submission not found"));
        submission.setGrade(request.grade());
        submission.setStatus(request.status());
        return mapper.toSubmissionDto(submissionRepository.save(submission));
    }

    public List<SubmissionResponse> findSubmissions(Long studentId, Long assignmentId) {
        if (studentId != null) {
            return submissionRepository.findByStudentId(studentId).stream().map(mapper::toSubmissionDto).toList();
        }
        if (assignmentId != null) {
            return submissionRepository.findByAssignmentId(assignmentId).stream().map(mapper::toSubmissionDto).toList();
        }
        return submissionRepository.findAll().stream().map(mapper::toSubmissionDto).toList();
    }

    private BaltabayAsselUser getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BaltabayAsselNotFoundException("User not found"));
    }

    private BaltabayAsselAssignment getAssignment(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new BaltabayAsselNotFoundException("Assignment not found"));
    }
}
