package com.example.springboottest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class OuterServiceImpl implements OuterService {

    private final MiddleService middleService;


    @Override
    public void callAndCatchException() {
        log.info("OuterService.callAndCatchException");
        try {
            middleService.callInnerMethod();
        } catch (Exception e) {
            log.error("OuterService.callAndCatchException", e);
        }
    }
}
