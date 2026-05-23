package kz.baltabay.assel.courseplatform.repository;

import java.util.List;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaltabayAsselAssignmentRepository extends JpaRepository<BaltabayAsselAssignment, Long> {
    List<BaltabayAsselAssignment> findByCourseId(Long courseId);
}
