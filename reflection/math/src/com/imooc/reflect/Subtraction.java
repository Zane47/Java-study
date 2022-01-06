package com.imooc.reflect;

public class Subtraction implements MathOperation {
    @Override
    public float operate(int a, int b) {
        System.out.println("operate subtraction");
        return a - b;
    }
}
