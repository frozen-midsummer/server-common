package com.wjx.common.exception;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SystemException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int code;

    public SystemException() {
        this(0, null);
    }

    public SystemException(final String message) {
        this(message, null);
    }

    public SystemException(final Throwable cause) {
        this(null, cause);
    }

    public SystemException(final int code, final String message) {
        super(message);
        this.code = code;
    }

    public SystemException(final String message, final Throwable cause) {
        this(0, message, cause);
    }

    public SystemException(final int code, final String message, final Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
