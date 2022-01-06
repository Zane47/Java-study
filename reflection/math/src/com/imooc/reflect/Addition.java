package com.imooc.reflect;

public class Addition implements MathOperation {
    @Override
    public float operate(int a, int b) {
        System.out.println("operate addition");
        return a + b;
    }
}
