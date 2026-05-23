package kz.baltabay.assel.courseplatform.dto;

import java.time.LocalDateTime;

public record BaltabayAsselFileDto(Long id, String originalFileName, String contentType, long size,
                                   LocalDateTime uploadedAt) {
}
