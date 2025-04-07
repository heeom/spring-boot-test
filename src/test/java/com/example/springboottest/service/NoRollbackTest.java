package com.example.springboottest.service;

import com.example.springboottest.domain.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class NoRollbackTest {

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
    public void noRollbackFor_shouldBeIgnoredByProxy() {
        String message = "no-rollback-test";
        Assertions.assertThrows(RuntimeException.class, () -> {
            callerService.caller(message);
        });

        boolean exists = logRepository.existsByMessage(message);
        Assertions.assertFalse(exists);
        Assertions.assertTrue(AopUtils.isCglibProxy(callerService));
    }
}