package kz.baltabay.assel.courseplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class BaltabayAsselOnlineCoursePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaltabayAsselOnlineCoursePlatformApplication.class, args);
    }
}
