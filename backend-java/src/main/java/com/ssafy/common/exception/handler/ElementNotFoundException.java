package com.ssafy.common.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class ElementNotFoundException extends RuntimeException{
    private String elementType;//클래스 이름
    private String value;// 값
}
