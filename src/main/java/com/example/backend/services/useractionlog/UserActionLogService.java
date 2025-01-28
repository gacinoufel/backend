package com.example.backend.services.useractionlog;

public interface UserActionLogService {
    void logUserAction(String username, String action, String method, String url, String ipAddress);
}