package kz.baltabay.assel.courseplatform.repository;

import java.util.List;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaltabayAsselEnrollmentRepository extends JpaRepository<BaltabayAsselEnrollment, Long> {
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    List<BaltabayAsselEnrollment> findByStudentId(Long studentId);
}
