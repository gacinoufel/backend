package com.example.backend.aspects;

import com.example.backend.services.useractionlog.UserActionLogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class UserTrackingAspect {

    private final UserActionLogService userActionLogService;

    @Pointcut("execution(* com.example.backend.controllers..*(..))")
    public void apiMethods() {
    }

    @Before("apiMethods() && args(.., request)")
    public void logUserAction(HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "UNKNOWN";

        if (principal instanceof User) {
            username = ((User) principal).getUsername();
        }
        String method = request.getMethod();
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String action = method + " " + url;
        userActionLogService.logUserAction(username, action, method, url, ip);
    }
}