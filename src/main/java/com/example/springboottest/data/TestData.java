package com.example.springboottest.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TestData {

    @JsonProperty("param1")
    private String param1;

    public String param2;
}

/**
 * 1. 다른 생성자가 없을 때 NoArgsConstructor 명시는 필수가 아님 -> 컴파일시 java가 자동 생성 해준다
 *
 * *** private 필드 기준
 * 2. @Getter (o) + @JsonProperty (x) -> 역직렬화 성공
 * 3. @Getter (x) + @JsonProperty (o) -> 역직렬화 성공
 * 4. @Getter (o) + @JsonProperty (o) -> 역직렬화 성공
 * 5. @Getter (x) + @JsonProperty (x) -> 역직렬화 실패
 *    - com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "key" (class com.example.springboottest.data.TestData), not marked as ignorable (0 known properties: ])
 *
 * *** public 필드 기준
 * 6. @Getter (x) + @JsonProperty (x) -> 역직렬화 성공
 */