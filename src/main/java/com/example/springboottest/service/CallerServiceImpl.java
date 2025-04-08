package com.example.springboottest.service;

import com.example.springboottest.domain.LogEntry;
import com.example.springboottest.domain.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

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
        log.info("[call] >>>>> TransactionInterceptor START (Advice - before method)");
        log.info("[call] isActualTransactionActive: {}", TransactionSynchronizationManager.isActualTransactionActive());
        log.info("[call] isNewTransaction: {}", TransactionAspectSupport.currentTransactionStatus().isNewTransaction());
        log.info("[call] isRollbackOnly (before): {}", TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());
        logRepository.save(new LogEntry(message));
        try {
            log.info("[call] targetService is proxy: {}", targetService.getClass()); // TargetServiceImpl$$SpringCGLIB$$0 proxy
            targetService.doSomething();
        } catch (RuntimeException e) {
            log.info("[call] caught exception: {}", e.getClass().getSimpleName());
        }
        log.info("[call] isRollbackOnly (after): {}", TransactionAspectSupport.currentTransactionStatus().isRollbackOnly());
        log.info("[call] <<<<< TransactionInterceptor END (Advice - after method)");
    }

    @Override
    @Transactional(noRollbackFor = RuntimeException.class)
    public void callNoRollback(String message) {
        logRepository.save(new LogEntry(message));
        log.info("targetService : {}", targetService.getClass());
        targetService.doSomething(message);
    }

    @Override
    @Transactional
    public void callCatchException(String message) {
        logRepository.save(new LogEntry(message));
        log.info("targetService : {}", targetService.getClass());
        try {
            targetService.doSomething(message);
        } catch (RuntimeException e) {
            log.info("[callCatchException] caught exception: {}", e.getClass().getSimpleName());
        }
    }
}
