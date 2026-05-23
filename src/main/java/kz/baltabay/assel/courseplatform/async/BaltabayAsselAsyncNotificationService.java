package kz.baltabay.assel.courseplatform.async;

import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BaltabayAsselAsyncNotificationService {
    @Async
    public CompletableFuture<Void> sendWelcomeEmail(String email) {
        log.info("Async welcome email queued for {}", email);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<Void> sendEnrollmentNotification(String email, String courseTitle) {
        log.info("Async enrollment notification queued for {} and course {}", email, courseTitle);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<String> generateSubmissionReport(Long submissionId) {
        String report = "Submission report generated for id " + submissionId;
        log.info("Async report generated: {}", report);
        return CompletableFuture.completedFuture(report);
    }
}
