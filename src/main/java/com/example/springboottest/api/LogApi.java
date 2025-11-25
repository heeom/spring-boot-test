package com.example.springboottest.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/log")
public class LogApi {

    @GetMapping
    public String log() {
        log.info("[LogApi] log");
        return "ok";
    }
}
