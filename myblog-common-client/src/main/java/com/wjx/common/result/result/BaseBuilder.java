package com.wjx.common.result.result;

import java.io.Serializable;

public abstract class BaseBuilder<B extends BaseBuilder<B, T>, T extends Serializable> {

    /**
     * 子类需要实现此方法以返回自身实例，支持链式调用
     */
    protected abstract B self();

    /**
     * 子类需要实现此方法以完成对象的构建
     */
    public abstract T build();
}