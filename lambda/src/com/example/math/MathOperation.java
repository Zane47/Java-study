package com.example.math;

/**
 * 四则运算接口
 */
@FunctionalInterface
public interface MathOperation {

    public Float operate(Integer v1, Integer v2);


}
