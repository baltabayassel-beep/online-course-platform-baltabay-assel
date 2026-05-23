package kz.baltabay.assel.courseplatform.mapper;

import kz.baltabay.assel.courseplatform.dto.BaltabayAsselCourseDtos.CourseRequest;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselCourseDtos.CourseResponse;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselCourse;
import org.springframework.stereotype.Component;

@Component
public class BaltabayAsselCourseMapper {
    public BaltabayAsselCourse toEntity(CourseRequest request) {
        BaltabayAsselCourse course = new BaltabayAsselCourse();
        apply(request, course);
        return course;
    }

    public void apply(CourseRequest request, BaltabayAsselCourse course) {
        course.setTitle(request.title());
        course.setDescription(request.description());
        course.setCategory(request.category());
        course.setLevel(request.level());
        course.setPrice(request.price());
        course.setPublished(request.published());
    }

    public CourseResponse toDto(BaltabayAsselCourse course) {
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCategory(),
                course.getLevel(),
                course.getPrice(),
                course.isPublished(),
                course.getInstructor().getId(),
                course.getInstructor().getFullName(),
                course.getCreatedAt());
    }
}
