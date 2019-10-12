package org.nure.julia.mappings;

public interface BasicMapper<F, T> {
    T map(F f);

    F reversalMap(T t);
}
