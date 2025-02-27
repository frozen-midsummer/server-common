package com.wjx.common.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SexEnum {
    M("M","男性"),
    F("F","女性"),
    O("O","其他")
    ;
    private final String code;
    private final String desc;
    public String code(){
        return this.code;
    }
}
