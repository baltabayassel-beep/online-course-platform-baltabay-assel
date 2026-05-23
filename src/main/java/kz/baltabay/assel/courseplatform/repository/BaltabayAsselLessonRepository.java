package kz.baltabay.assel.courseplatform.repository;

import java.util.List;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselLesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaltabayAsselLessonRepository extends JpaRepository<BaltabayAsselLesson, Long> {
    List<BaltabayAsselLesson> findByCourseIdOrderByPositionAsc(Long courseId);
}
