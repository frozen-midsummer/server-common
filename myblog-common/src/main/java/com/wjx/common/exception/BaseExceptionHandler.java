package com.wjx.common.exception;

import com.wjx.common.result.result.ApiErrorCode;
import com.wjx.common.result.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import java.util.Arrays;

@Slf4j
public abstract class BaseExceptionHandler {
    /**
     * 切点方法
     */
    public abstract void serviceMethods();

    @Around("serviceMethods()")
    public Object execute(final ProceedingJoinPoint pjp) {
        try {
            return pjp.proceed();
        } catch (SystemException systemException) {
            log.error("业务异常，错误码：{}，错误信息：{}，方法：{}，入参：{}", systemException.getCode(), systemException.getMessage(), pjp.getSignature().toString(), Arrays.toString(pjp.getArgs()), systemException);
            return ApiResult.Builder.newBuilder().fail(systemException.getCode(), systemException.getMessage()).build();
        } catch (Throwable throwable) {
            log.error("全局未处理异常，方法：{}，入参：{}", pjp.getSignature().toString(), Arrays.toString(pjp.getArgs()), throwable);
            return ApiResult.Builder.newBuilder().fail(ApiErrorCode.SERVER_ERROR.code(), "意外错误，请联系系统管理员").build();
        }
    }
}
