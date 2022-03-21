package com.example.springbootlab.controller.exception;

import com.example.springbootlab.exception.AccessDeniedException;
import com.example.springbootlab.exception.AuthenticationEntryPointException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
