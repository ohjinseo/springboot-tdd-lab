package com.example.springbootlab.aop;

import com.example.springbootlab.config.security.guard.AuthHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

@Aspect
@Component // ioc에 등록
@RequiredArgsConstructor
@Slf4j
public class AssignMemberIdAspect {
    private final AuthHelper authHelper;

    @Before("@annotation(com.example.springbootlab.aop.AssignMemberId)")
    public void assignMemberId(JoinPoint joinPoint) {
        Arrays.stream(joinPoint.getArgs())
                .forEach(arg->getMethod(arg.getClass(), "setMemberId")
                        .ifPresent(setMemberId->invokeMethod(arg, setMemberId, authHelper.extractMemberId())));
    }

    private Optional<Method> getMethod(Class<?> clazz, String methodName) {
        try{
            return Optional.of(clazz.getMethod(methodName, Long.class));
        }catch(NoSuchMethodException e){
            return Optional.empty();
        }
    }

    private void invokeMethod(Object obj, Method method, Object... args) {
        try {
            method.invoke(obj, args);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
