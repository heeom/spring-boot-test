package com.example.springboottest.api;

import com.example.springboottest.data.ModelData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ModelAttrController {

    @PostMapping("/model")
    public String handleModelAttr(@ModelAttribute ModelData data) {
        log.info(data.toString());
        return "ok";
    }
}
