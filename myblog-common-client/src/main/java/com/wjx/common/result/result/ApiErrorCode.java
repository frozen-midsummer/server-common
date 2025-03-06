package com.wjx.common.result.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiErrorCode {
    OK(0),
    SERVER_ERROR(500),
    ;
    private final int code;

    public int code() {
        return this.code;
    }
}
