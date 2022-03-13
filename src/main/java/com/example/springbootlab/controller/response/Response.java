package com.example.springbootlab.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL) // null 값을 가지는 필드는 JSON 응답에서 제외
@AllArgsConstructor(access= AccessLevel.PRIVATE) // static method 내에서 인스턴스를 생성하므로, 외부에서 생성자의 접근레벨을 차단
@Getter // 응답 객체를 JSON 으로 변환하기위해 선언
public class Response {
    private boolean success;
    private int code;
    private Result result;

    // 요청 성공 시 응답 코드 0 리턴
    public static Response success() {
        return new Response(true, 0, null);
    }

    public static<T> Response success(T data){
        return new Response(true, 0, new Success<T>(data));
    }

    public static Response failure(int code, String msg) {
        return new Response(false, code, new Failure(msg));
    }
}
