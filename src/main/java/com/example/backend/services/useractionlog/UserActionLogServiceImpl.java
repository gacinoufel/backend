package com.example.backend.services.useractionlog;

import com.example.backend.entities.UserActionLog;
import com.example.backend.repositories.UserActionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserActionLogServiceImpl implements UserActionLogService {

    private final UserActionLogRepository userActionLogRepository;

    @Override
    @Transactional
    public void logUserAction(String username, String action, String method, String url, String ipAddress) {
        UserActionLog log = new UserActionLog();
        log.setUsername(username);
        log.setAction(action);
        log.setMethod(method);
        log.setUrl(url);
        log.setIpAddress(ipAddress);

        userActionLogRepository.save(log);
    }
}