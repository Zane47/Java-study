package com.imooc.reflect;

public class Addition implements MathOperation {

    private static final String FILE_NAME = "fileName";

    public Integer age;

    public Addition() {
    }

    public Addition(Integer age) {
        this.age = age;
    }

    @Override
    public float operate(int a, int b) {
        System.out.println("operate addition");
        return a + b;
    }
}
