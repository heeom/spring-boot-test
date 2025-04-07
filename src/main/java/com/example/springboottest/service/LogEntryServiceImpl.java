package com.example.springboottest.service;

import com.example.springboottest.domain.LogEntry;
import com.example.springboottest.domain.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LogEntryServiceImpl implements LogEntryService {

    private final LogRepository logRepository;

    @Override
    public void save(String message) {
        log.info("LogEntryService.save : {}", message);
        logRepository.save(new LogEntry(message));
    }
}
