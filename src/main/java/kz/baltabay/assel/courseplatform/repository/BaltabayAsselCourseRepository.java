package kz.baltabay.assel.courseplatform.repository;

import kz.baltabay.assel.courseplatform.entity.BaltabayAsselCourse;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselCourseLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BaltabayAsselCourseRepository extends JpaRepository<BaltabayAsselCourse, Long> {
    @Query("""
            select c from BaltabayAsselCourse c
            where (:search is null or lower(c.title) like lower(concat('%', :search, '%'))
                   or lower(c.description) like lower(concat('%', :search, '%')))
              and (:category is null or lower(c.category) = lower(:category))
              and (:level is null or c.level = :level)
              and (:published is null or c.published = :published)
            """)
    Page<BaltabayAsselCourse> searchCourses(
            @Param("search") String search,
            @Param("category") String category,
            @Param("level") BaltabayAsselCourseLevel level,
            @Param("published") Boolean published,
            Pageable pageable);
}
