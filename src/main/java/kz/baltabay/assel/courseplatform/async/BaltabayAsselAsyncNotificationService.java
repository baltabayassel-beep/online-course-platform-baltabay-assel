package kz.baltabay.assel.courseplatform.async;

import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BaltabayAsselAsyncNotificationService {
    private static final Logger log = LoggerFactory.getLogger(BaltabayAsselAsyncNotificationService.class);

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
