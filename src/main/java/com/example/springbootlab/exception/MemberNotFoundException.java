package com.example.springbootlab.exception;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(){
        super("유저를 찾지 못하였습니다");
    }
}
