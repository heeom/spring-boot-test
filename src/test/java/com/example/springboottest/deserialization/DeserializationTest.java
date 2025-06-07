package com.example.springboottest.deserialization;

import com.example.springboottest.data.TestData;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DeserializationTest {

    private ObjectMapper objectMapper;
    private final String message = "{\"key\": \"value\", \"value\": \"key\"}";
    private final String testDataMessage = "{\"param1\": \"value1\", \"param2\": \"value2\"}";

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("역직렬화 대상 클래스에 기본생성자가 존재하지 않으면 역직렬화시 예외가 발생한다")
    public void notExistsNoArgsConstructorThenThrowException() throws JsonProcessingException {
        Assertions.assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(message, Message.class));
    }

    @Test
    @DisplayName("기본생성자 없이 생성자 기반 역직렬화를 사용하면 예외 발생하지 않는다")
    public void deserializeWithJsonCreator() throws JsonProcessingException {
        Example result = objectMapper.readValue(message, Example.class);
        Assertions.assertEquals("value", result.getKey());
    }

    @Test
    @DisplayName("@NoArgsConstructor (X), @Getter (O) private 필드는 역직렬화에 성공한다.")
    public void deserializeWithNoArgsConstructorThenSuccess() throws JsonProcessingException {
        com.example.springboottest.data.TestData testData = objectMapper.readValue(testDataMessage, com.example.springboottest.data.TestData.class);
        Assertions.assertEquals("value2", testData.getParam2());
    }

    @Test
    @DisplayName("private 필드 기준 기본생성자 X, getter X, JsonProperty X, ObjectMapper FAIL_ON_UNKNOWN_PROPERTIES = false 이면 역직렬화 성공한다.")
    public void deserializeSuccessTest() throws JsonProcessingException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Message result = objectMapper.readValue(message, Message.class);
        Assertions.assertNull(result.key);
        Assertions.assertEquals("key", result.value);
    }

    static class Message {
        private String key;
        public String value;
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

    @Getter
    static class TestData {
        @JsonProperty("key")
        private String key;
    }
}
