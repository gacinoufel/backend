package com.example.backend.aspects;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class UserTrackingAspect {

    private static final Logger logger = Logger.getLogger(UserTrackingAspect.class.getName());

    @Pointcut("execution(* com.example.backend.controllers..*(..)) && args(.., request)")
    public void apiMethods(HttpServletRequest request) {
    }

    @Before("apiMethods(request)")
    public void logUserAction(HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof User ? ((User) principal).getUsername() : "UNKNOWN";

        if (!(principal instanceof User)) {
            logger.warning("L'utilisateur n'est pas authentifiée ni reconnu");
        }

        String method = request.getMethod();
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();

        logger.info(() -> String.format("L'utilisateur %s a effectué une requête %s sur l'URL %s depuis l'IP %s",
                username, method, url, ip));
    }
}