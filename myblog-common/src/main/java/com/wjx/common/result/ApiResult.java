package com.wjx.common.result;

import com.wjx.common.builder.BaseBuilder;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResult<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int errorNo = 0;
    private String errorInfo = "";
    private T result;

    @Data
    public static class Builder<D extends Serializable> extends BaseBuilder<Builder<D>, ApiResult<D>> {
        private int errorNo;
        private String errorInfo = "";
        private D result;

        public static <D extends Serializable> Builder<D> newBuilder() {
            return new Builder<>();
        }

        public Builder<D> ok() {
            return errorNo(0).errorInfo("OK");
        }

        public Builder<D> ok(D result) {
            return ok().result(result);
        }

        public Builder<D> fail(int errorNo, String errorInfo) {
            return ok().errorNo(errorNo).errorInfo(errorInfo).result(result);
        }

        public Builder<D> errorNo(int errorNo) {
            this.errorNo = errorNo;
            return this;
        }

        public Builder<D> errorInfo(String errorInfo) {
            this.errorInfo = errorInfo;
            return this;
        }

        public Builder<D> result(D result) {
            this.result = result;
            return this;
        }

        @Override
        protected Builder<D> self() {
            return this;
        }

        @Override
        public ApiResult<D> build() {
            ApiResult<D> res = new ApiResult<>();
            res.setErrorNo(this.errorNo);
            res.setErrorInfo(this.errorInfo);
            res.setResult(this.result);
            return res;
        }
    }
}
