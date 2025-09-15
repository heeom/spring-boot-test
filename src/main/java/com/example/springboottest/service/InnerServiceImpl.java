package com.example.springboottest.service;

import com.example.springboottest.domain.LogEntry;
import com.example.springboottest.domain.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class InnerServiceImpl implements InnerService {

    private final LogRepository logRepository;

    @Override
    @Transactional
    public void throwException() {
        LogEntry logEntry = logRepository.findById(1L)
                .orElse(logRepository.save(new LogEntry("inner")));

        log.info("InnerService.throwException {}", logEntry);

        if (true) {
            log.error("InnerService.throwException throw RuntimeException");
            throw new RuntimeException();
        }
    }
}
