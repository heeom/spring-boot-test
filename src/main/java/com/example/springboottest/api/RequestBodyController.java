package com.example.springboottest.api;

import com.example.springboottest.data.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RequestBodyController {

    @PostMapping("/json")
    public String handleJson(@RequestBody JsonData data) {
        log.info("handle json data : {}", data);
        return "ok";
    }
}
