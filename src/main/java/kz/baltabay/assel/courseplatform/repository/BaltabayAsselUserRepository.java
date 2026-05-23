package kz.baltabay.assel.courseplatform.repository;

import java.util.Optional;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaltabayAsselUserRepository extends JpaRepository<BaltabayAsselUser, Long> {
    Optional<BaltabayAsselUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
