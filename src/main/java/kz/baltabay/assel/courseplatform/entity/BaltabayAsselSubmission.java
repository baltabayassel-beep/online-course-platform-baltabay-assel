package kz.baltabay.assel.courseplatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
public class BaltabayAsselSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private BaltabayAsselAssignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private BaltabayAsselUser student;

    @Column(nullable = false, length = 5000)
    private String answerText;

    private Integer grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaltabayAsselSubmissionStatus status = BaltabayAsselSubmissionStatus.SUBMITTED;

    @Column(nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BaltabayAsselAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(BaltabayAsselAssignment assignment) {
        this.assignment = assignment;
    }

    public BaltabayAsselUser getStudent() {
        return student;
    }

    public void setStudent(BaltabayAsselUser student) {
        this.student = student;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public BaltabayAsselSubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(BaltabayAsselSubmissionStatus status) {
        this.status = status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}
