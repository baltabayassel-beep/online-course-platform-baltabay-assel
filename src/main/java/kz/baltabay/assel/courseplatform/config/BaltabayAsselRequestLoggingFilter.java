package kz.baltabay.assel.courseplatform.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class BaltabayAsselRequestLoggingFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(BaltabayAsselRequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        long started = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            log.info("{} {} -> {} in {} ms", request.getMethod(), request.getRequestURI(), response.getStatus(),
                    System.currentTimeMillis() - started);
        }
    }
}
