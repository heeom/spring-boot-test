package com.example.springboottest.service;

import com.example.springboottest.domain.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

@Slf4j
@SpringBootTest
class noRollbackTest {

    @Autowired
    CallerService callerService;

    @Autowired
    LogRepository logRepository;

    @BeforeEach
    void setUp() {
        logRepository.deleteAll();
    }

    @Test
    @DisplayName("호출하는 메서드에만 @Transactional이 있는 경우 호출 되는 메서드는 프록시 생성 안됨 -> noRollback 설정 적용됨")
    void noRollbackFor_shouldBeCommitted() {
        String message = "no-rollback-test";
        Assertions.assertThrows(RuntimeException.class, () -> {
            callerService.callNoRollback(message);
        });
        // class com.example.springboottest.service.TargetServiceImpl$$SpringCGLIB$$0

        boolean exists = logRepository.existsByMessage(message);
        Assertions.assertTrue(exists);
        Assertions.assertTrue(AopUtils.isCglibProxy(callerService));
    }

    @Test
    @DisplayName("@Transactional 이 호출하는 메서드에만 있는 경우 target 메서드에서 발생한 예외를 caller 메서드에서 catch 하면 커밋됨")
    void catchException_shouldBeCommitted() {
        String message = "catch-rollback-test";

        Assertions.assertDoesNotThrow(() -> {callerService.callCatchException(message);});

        boolean exists = logRepository.existsByMessage(message);
        Assertions.assertTrue(exists);
    }
}