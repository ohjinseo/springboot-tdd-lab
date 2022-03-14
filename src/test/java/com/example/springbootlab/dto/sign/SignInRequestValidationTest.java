package com.example.springbootlab.dto.sign;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Iterator;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

// SignUpRequest와 SignInRequest의 유효성 검사는 컨트롤러 계층에서 MockMvc를 이용하여 수행해도 되지만
// 컨트롤러를 로드하기 위한 비용이 들기 때문에 별도로 테스트 수행
class SignInRequestValidationTest {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validateTest(){
        // given
        SignInRequest req = createRequest();

        // when
        // 검증을 수행하고 제약 조건에 위한한 내용들을 응답 결과로 받는다
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isEmpty(); // 위반한 내용들이 없다면 비어있음
    }

    @Test // 이메일 제약 조건에 위반한 값을 넣었을 때 테스트
    void invalidateByNotFormattedEmailTest(){
        // given
        String invalidValue = "email";
        SignInRequest req = createRequestWithEmail(invalidValue);

        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty(); // 이메일 제약 조건을 위반했으므로 결과값이 담김
        // InvalidateValue를 필터링한 요소만 수집해서 조건을 위반한 값이 포함되어있는지 확인
        // email 필드에 null 값이 들어간 경우, 실제로 2개의 제약 조건을 위반함
        // 하지만 실제로는 1개의 제약 조건만 응답 -> 어떤 제약 조건을 위반하는지 정확히 기대한 값을 돌려주지 않기 때문에 notEmpty()로 검증

        assertThat(validate.stream().map(ConstraintViolation::getInvalidValue).collect(toSet())).contains(invalidValue);
    }

    @Test
    void invalidateByEmptyEmailTest() {
        // given
        String invalidValue = null;
        SignInRequest req = createRequestWithEmail(invalidValue);

        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(ConstraintViolation::getInvalidValue).collect(toSet())).contains(invalidValue);
    }

    @Test
    void invalidateByBlankEmailTest() {
        // given
        String invalidValue ="";
        SignInRequest req = createRequestWithEmail(invalidValue);

        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(ConstraintViolation::getInvalidValue).collect(toSet())).contains(invalidValue);
    }

    @Test
    void invalidateByEmptyPasswordTest() {
        // given
        String invalidValue = null;
        SignInRequest req = createRequestWithPassword(invalidValue);

        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(ConstraintViolation::getInvalidValue).collect(toSet())).contains(invalidValue);
    }

    @Test
    void invalidateByBlankPasswordTest() {
        // given
        String invalidValue = " ";
        SignInRequest req = createRequestWithPassword(invalidValue);

        // when
        Set<ConstraintViolation<SignInRequest>> validate = validator.validate(req);

        // then
        assertThat(validate).isNotEmpty();
        assertThat(validate.stream().map(ConstraintViolation::getInvalidValue).collect(toSet())).contains(invalidValue);
    }

    // 정상적인 요청 객체를 생성하는 팩토리 메소드
    private SignInRequest createRequest(){
        return new SignInRequest("email@email.com", "123456a!");
    }

    private SignInRequest createRequestWithEmail(String email){
        return new SignInRequest(email, "123456a!");
    }

    private SignInRequest createRequestWithPassword(String password){
        return new SignInRequest("eamil@email.com", password);
    }
}