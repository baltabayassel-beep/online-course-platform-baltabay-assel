package kz.baltabay.assel.courseplatform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
public class BaltabayAsselEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private BaltabayAsselUser student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private BaltabayAsselCourse course;

    @Column(nullable = false)
    private LocalDateTime enrolledAt = LocalDateTime.now();

    @Column(nullable = false)
    private int progressPercent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BaltabayAsselUser getStudent() {
        return student;
    }

    public void setStudent(BaltabayAsselUser student) {
        this.student = student;
    }

    public BaltabayAsselCourse getCourse() {
        return course;
    }

    public void setCourse(BaltabayAsselCourse course) {
        this.course = course;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public int getProgressPercent() {
        return progressPercent;
    }

    public void setProgressPercent(int progressPercent) {
        this.progressPercent = progressPercent;
    }
}
