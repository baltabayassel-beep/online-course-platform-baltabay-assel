package kz.baltabay.assel.courseplatform.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BaltabayAsselGlobalExceptionHandler {
    @ExceptionHandler(BaltabayAsselNotFoundException.class)
    public ResponseEntity<BaltabayAsselErrorResponse> notFound(BaltabayAsselNotFoundException ex,
                                                               HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
    }

    @ExceptionHandler(BaltabayAsselBadRequestException.class)
    public ResponseEntity<BaltabayAsselErrorResponse> badRequest(BaltabayAsselBadRequestException ex,
                                                                 HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), request, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaltabayAsselErrorResponse> validation(MethodArgumentNotValidException ex,
                                                                 HttpServletRequest request) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(error -> error.getField(), error -> error.getDefaultMessage(),
                        (left, right) -> left));
        return build(HttpStatus.BAD_REQUEST, "Validation failed", request, errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaltabayAsselErrorResponse> unexpected(Exception ex, HttpServletRequest request) {
        log.error("Unhandled error on {}", request.getRequestURI(), ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", request, null);
    }

    private ResponseEntity<BaltabayAsselErrorResponse> build(HttpStatus status, String message,
                                                            HttpServletRequest request,
                                                            Map<String, String> validationErrors) {
        return ResponseEntity.status(status).body(new BaltabayAsselErrorResponse(
                LocalDateTime.now(), status.value(), status.getReasonPhrase(), message, request.getRequestURI(),
                validationErrors));
    }
}
