package com.example.springboottest.deserialization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeserializationTest {

    private ObjectMapper objectMapper;
    private final String message = "{\"key\": \"value\"}";

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("역직렬화 대상 클래스에 기본생성자가 존재하지 않으면 역직렬화시 예외가 발생한다")
    public void notExistsNoArgsConstructorThenThrowException() {
        Assertions.assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(message, Message.class));
    }

    @Test
    @DisplayName("기본생성자 없이 생성자 기반 역직렬화를 사용하면 예외 발생하지 않는다")
    public void deserializeWithJsonCreator() throws JsonProcessingException {
        Example result = objectMapper.readValue(message, Example.class);
        Assertions.assertEquals("value", result.getKey());
    }

    static class Message {
        private String key;
    }

    static class Example {
        private final String key;

        @JsonCreator
        public Example(@JsonProperty("key") String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}
