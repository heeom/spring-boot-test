package com.example.springboottest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Service
@RequiredArgsConstructor
public class TargetServiceImpl implements TargetService {

    @Override
    @Transactional
    public void doSomething() {
        log.info("{} : doSomething() - isNewTransaction: {}", this.getClass(), TransactionAspectSupport.currentTransactionStatus().isNewTransaction());
        log.info("{} : doSomething() - isActualTransactionActive: {}", this.getClass(), TransactionSynchronizationManager.isActualTransactionActive());
        log.info("TargetService.doSomething() - getCurrentTransactionName: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        log.info("TargetServiceImpl doSomething throw RuntimeException");
        throw new RuntimeException();
    }

    @Override
    public void doSomething(String message) {
        log.info("TargetService.doSomething({}) - isNewTransaction: {}", message, TransactionAspectSupport.currentTransactionStatus().isNewTransaction());
        log.info("TargetService.doSomething({}) - isActualTransactionActive: {}", message, TransactionSynchronizationManager.isActualTransactionActive());
        log.info("TargetService.doSomething({}) - getCurrentTransactionName: {}", message, TransactionSynchronizationManager.getCurrentTransactionName());

        throw new RuntimeException(message);
    }
}
