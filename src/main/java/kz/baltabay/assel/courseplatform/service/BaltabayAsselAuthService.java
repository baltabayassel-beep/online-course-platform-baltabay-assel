package kz.baltabay.assel.courseplatform.service;

import java.util.Set;
import java.util.stream.Collectors;
import kz.baltabay.assel.courseplatform.async.BaltabayAsselAsyncNotificationService;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselAuthDtos.AuthResponse;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselAuthDtos.LoginRequest;
import kz.baltabay.assel.courseplatform.dto.BaltabayAsselAuthDtos.RegisterRequest;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselRole;
import kz.baltabay.assel.courseplatform.entity.BaltabayAsselUser;
import kz.baltabay.assel.courseplatform.exception.BaltabayAsselBadRequestException;
import kz.baltabay.assel.courseplatform.mapper.BaltabayAsselUserMapper;
import kz.baltabay.assel.courseplatform.repository.BaltabayAsselUserRepository;
import kz.baltabay.assel.courseplatform.security.BaltabayAsselJwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BaltabayAsselAuthService {
    private static final Logger log = LoggerFactory.getLogger(BaltabayAsselAuthService.class);

    private final BaltabayAsselUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final BaltabayAsselJwtUtil jwtUtil;
    private final BaltabayAsselUserMapper userMapper;
    private final BaltabayAsselAsyncNotificationService notificationService;

    public BaltabayAsselAuthService(BaltabayAsselUserRepository userRepository,
                                    PasswordEncoder passwordEncoder,
                                    AuthenticationManager authenticationManager,
                                    UserDetailsService userDetailsService,
                                    BaltabayAsselJwtUtil jwtUtil,
                                    BaltabayAsselUserMapper userMapper,
                                    BaltabayAsselAsyncNotificationService notificationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
        this.notificationService = notificationService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BaltabayAsselBadRequestException("Email already registered");
        }
        BaltabayAsselUser user = new BaltabayAsselUser();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(resolveRoles(request.roles()));
        BaltabayAsselUser saved = userRepository.save(user);
        notificationService.sendWelcomeEmail(saved.getEmail());
        log.info("Registered user {}", saved.getEmail());
        UserDetails userDetails = userDetailsService.loadUserByUsername(saved.getEmail());
        return new AuthResponse(jwtUtil.generateToken(userDetails), "Bearer", userMapper.toDto(saved));
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        BaltabayAsselUser user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BaltabayAsselBadRequestException("Invalid credentials"));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        log.info("User logged in {}", user.getEmail());
        return new AuthResponse(jwtUtil.generateToken(userDetails), "Bearer", userMapper.toDto(user));
    }

    private Set<BaltabayAsselRole> resolveRoles(Set<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return Set.of(BaltabayAsselRole.ROLE_STUDENT);
        }
        return roles.stream()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .map(String::toUpperCase)
                .map(BaltabayAsselRole::valueOf)
                .collect(Collectors.toSet());
    }
}
