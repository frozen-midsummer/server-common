package com.wjx.common.result.enumeration;

import java.util.EnumSet;
import java.util.Optional;

public interface Enumeration<T> {
    T code();

    String describe();

    static <E extends Enum<E> & Enumeration<T>, T> E codeOf(T code, Class<E> clazz) {
        if (code == null) {
            return null;
        }
        EnumSet<E> allEnums = EnumSet.allOf(clazz);
        return allEnums.stream()
                .filter(e -> e.code().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("枚举：[%s]不存在枚举项：[%s]", clazz.getName(), code)));
    }

    static <E extends Enumeration<T>, T> T getCodeOrDefault(E value) {
        return Optional.ofNullable(value)
                .map(Enumeration::code)
                .orElse(null);
    }
}
