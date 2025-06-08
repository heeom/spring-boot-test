package com.example.springboottest.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
public class JsonData {

    @JsonProperty("key")
    private String key;

    public JsonData(String key) {
        this.key = key;
    }
}
