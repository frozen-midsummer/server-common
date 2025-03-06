package com.wjx.common.rpc;


import com.wjx.common.result.result.ApiResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseService {
    protected <T extends Serializable> ApiResult<T> ok() {
        return ApiResult.Builder.<T>newBuilder().ok().build();
    }

    protected <T extends Serializable> ApiResult<T> ok(T data) {
        return ApiResult.Builder.<T>newBuilder().ok(data).build();
    }

    protected <T extends Serializable> ApiResult<ArrayList<T>> ok(List<T> data) {
        ArrayList<T> result = new ArrayList<>(data);
        return ApiResult.Builder.<ArrayList<T>>newBuilder().ok(result).build();
    }

    protected <T extends Serializable> ApiResult<T> fail(int errorNo, String msg) {
        return ApiResult.Builder.<T>newBuilder().fail(errorNo, msg).build();
    }
}
