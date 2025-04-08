package com.example.springboottest.service;

import com.example.springboottest.domain.LogEntry;
import com.example.springboottest.domain.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallerServiceImpl implements CallerService {

    private final TargetService targetService;
    private final LogRepository logRepository;

    @Override
    @Transactional(noRollbackFor = RuntimeException.class)
    public void call(String message) {
        log.info("call() - isNewTransaction: {}", TransactionAspectSupport.currentTransactionStatus().isNewTransaction());
        log.info("targetService is proxy: {}", targetService.getClass()); // TargetServiceImpl$$SpringCGLIB$$0 proxy
        logRepository.save(new LogEntry(message));
        targetService.doSomething();
    }

    @Override
    @Transactional
    public void callAndCatchException(String message) {
        log.info("targetService is proxy: {}", targetService.getClass()); // TargetServiceImpl$$SpringCGLIB$$0 proxy
        logRepository.save(new LogEntry(message));
        try {
            targetService.doSomething();
        } catch (RuntimeException e) {
            log.info("catch RuntimeException");
        }
        log.info("{} : caller() END - isCurrentTransactionRollbackOnly: {}", this.getClass(), TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());
    }
}
