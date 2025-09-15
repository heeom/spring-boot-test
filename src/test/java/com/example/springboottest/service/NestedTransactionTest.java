package com.example.springboottest.service;

import com.example.springboottest.domain.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

@Slf4j
@SpringBootTest
public class NestedTransactionTest {

    private static final String INNER_MESSAGE = "throwException test message";
    private static final String MIDDLE_MESSAGE = "callInnerMethod test message";

    @Autowired
    private InnerService innerService;
    @Autowired
    private MiddleService middleService;
    @Autowired
    private OuterService outerService;
    @Autowired
    private LogRepository logRepository;

    @Nested
    @DisplayName("모든 클래스가 @Transactional을 보유하고 있을때")
    class all_method_has_nested_transaction_then {

        @Nested
        @DisplayName("가장 상위 메서드에서 예외를 catch해도")
        class outer_service_catch_exception {


            @Test
            @DisplayName("예외를 throw한 메서드의 로직은 롤백된다.")
            void caller_service_method_rollback() {
                Assertions.assertThrows(UnexpectedRollbackException.class, () -> outerService.callAndCatchException());
            }
        }
    }
}
