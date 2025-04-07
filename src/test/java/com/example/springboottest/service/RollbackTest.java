package com.example.springboottest.service;

import com.example.springboottest.domain.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransactionException;
import org.junit.jupiter.api.*;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

@Slf4j
@SpringBootTest
class RollbackTest {

    @Autowired
    CallerService callerService;

    @Autowired
    LogRepository logRepository;

    @BeforeEach
    void setUp() {
        logRepository.deleteAll();
    }

    @Test
    @DisplayName("caller 클래스 메서드에서 target 클래스 메서드를 호출했을 때 " +
            "target 클래스에서 발생한 예외에 대해 caller 메서드에서 noRollback 설정한 경우 " +
            "noRollback 설정은 무시됨")
    void noRollbackFor_shouldBeIgnored() {
        String message = "no-rollback-test";
        Assertions.assertThrows(RuntimeException.class, () -> {
            callerService.call(message);
        });

        // caller proxy : CallerServiceImpl$$SpringCGLIB$$0.caller
        // target proxy : TargetServiceImpl$$SpringCGLIB$$0.doSomething
        // TransactionInterceptor : Application exception overridden by commit exception -> rollbackOnly가 마킹되어서 커밋되지 않고 롤백됨

        boolean exists = logRepository.existsByMessage(message);
        Assertions.assertFalse(exists);
        Assertions.assertTrue(AopUtils.isCglibProxy(callerService));
    }

    @Test
    @DisplayName("target 메서드에서 발생한 예외를 caller 메서드에서 catch 해도 커밋되지 않고 롤백됨")
    void catchException_shouldStillRollback() {
        String message = "catch-rollback-test";
        Assertions.assertThrows(UnexpectedRollbackException.class, () -> {
            callerService.callAndCatchException(message); // TransactionException 발생
        });

        boolean exists = logRepository.existsByMessage(message);
        Assertions.assertFalse(exists);
    }
}