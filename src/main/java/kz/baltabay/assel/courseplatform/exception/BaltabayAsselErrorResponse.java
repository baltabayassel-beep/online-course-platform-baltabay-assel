package kz.baltabay.assel.courseplatform.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record BaltabayAsselErrorResponse(LocalDateTime timestamp, int status, String error, String message,
                                         String path, Map<String, String> validationErrors) {
}
