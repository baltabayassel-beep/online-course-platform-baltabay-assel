package kz.baltabay.assel.courseplatform.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselCourseLevel;

public class BaltabayAsselCourseDtos {
    public record CourseRequest(
            @NotBlank String title,
            @NotBlank String description,
            @NotBlank String category,
            @NotNull BaltabayAsselCourseLevel level,
            @NotNull @DecimalMin("0.0") BigDecimal price,
            boolean published,
            @NotNull Long instructorId) {
    }

    public record CourseResponse(
            Long id,
            String title,
            String description,
            String category,
            BaltabayAsselCourseLevel level,
            BigDecimal price,
            boolean published,
            Long instructorId,
            String instructorName,
            LocalDateTime createdAt) {
    }
}
