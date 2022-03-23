package com.example.springbootlab.controller.exception;

import com.example.springbootlab.exception.AccessDeniedException;
import com.example.springbootlab.exception.AuthenticationEntryPointException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore // 단순 예외를 advice에서 잡아내기 위해 작성된 것이므로 문서화할 필요X
@RestController
public class ExceptionController {
    @GetMapping("/exception/entry-point")
    public void entryPoint(){
        throw new AuthenticationEntryPointException();
    }

    @GetMapping("/exception/access-denied")
    public void accessDenied(){
        throw new AccessDeniedException();
    }
}
