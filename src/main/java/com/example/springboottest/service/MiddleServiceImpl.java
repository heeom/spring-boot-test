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
public class MiddleServiceImpl implements MiddleService {

    private final InnerService innerService;
    private final LogRepository logRepository;

    @Override
    @Transactional(noRollbackFor = RuntimeException.class)
    public void callInnerMethod() {
        log.info("MiddleService.callInnerMethod");
        logRepository.save(new LogEntry("Middle"));
        innerService.throwException();
//        throw new RuntimeException("MiddleService.throw RuntimeException");
    }
}
