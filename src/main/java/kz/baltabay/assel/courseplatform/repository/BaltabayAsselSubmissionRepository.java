package kz.baltabay.assel.courseplatform.repository;

import java.util.List;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaltabayAsselSubmissionRepository extends JpaRepository<BaltabayAsselSubmission, Long> {
    List<BaltabayAsselSubmission> findByStudentId(Long studentId);

    List<BaltabayAsselSubmission> findByAssignmentId(Long assignmentId);
}
