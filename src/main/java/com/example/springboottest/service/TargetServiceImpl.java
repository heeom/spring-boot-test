package com.example.springboottest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TargetServiceImpl implements TargetService {

    @Override
    public void doSomething() {
        log.info("TargetServiceImpl doSomething throw RuntimeException");
        throw new RuntimeException();
    }
}
