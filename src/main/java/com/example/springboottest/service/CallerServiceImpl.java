package com.example.springboottest.service;

import com.example.springboottest.domain.LogEntry;
import com.example.springboottest.domain.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CallerServiceImpl implements CallerService {

    private final TargetService targetService;
    private final LogRepository logRepository;

    @Override
    @Transactional(noRollbackFor = RuntimeException.class)
    public void caller(String message) {
        logRepository.save(new LogEntry(message));
        targetService.doSomething();
    }
}
