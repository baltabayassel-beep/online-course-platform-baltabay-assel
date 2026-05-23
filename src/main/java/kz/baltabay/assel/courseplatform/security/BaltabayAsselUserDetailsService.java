package kz.baltabay.assel.courseplatform.security;

import kz.baltabay.assel.courseplatform.entity.BaltabayAsselUser;
import kz.baltabay.assel.courseplatform.repository.BaltabayAsselUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BaltabayAsselUserDetailsService implements UserDetailsService {
    private final BaltabayAsselUserRepository userRepository;

    public BaltabayAsselUserDetailsService(BaltabayAsselUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        BaltabayAsselUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
    }
}
