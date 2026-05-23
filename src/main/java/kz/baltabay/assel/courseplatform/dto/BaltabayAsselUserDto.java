package kz.baltabay.assel.courseplatform.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record BaltabayAsselUserDto(Long id, String fullName, String email, Set<String> roles, LocalDateTime createdAt) {
}
