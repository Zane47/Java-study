package com.example.math;

import sun.nio.cs.ext.MacHebrew;

public class LambdaSample {
    public static void main(String[] args) {
        // ------------------------ 1. 标准使用方式 ------------------------
        // 约束条件：Lambda表达式只能实现有且只有一个抽象方法的接口，Java称之为"函数式接口"
        MathOperation addition = (Integer v1, Integer v2) -> {
            System.out.println("addition operation");
            return v1 + v2 + 0f;
        };
        System.out.println(addition.operate(5, 3));

        // ------------------------ 2. Lambda允许忽略参数类型 ------------------------
        MathOperation substraction = (v1, v2) -> {
            System.out.println("substraction operation");
            return v1 - v2 - 0f;
        };
        System.out.println(substraction.operate(5, 3));

        // ------------------------ 3. 单行实现代码可以省略大括号和return ------------------------
        MathOperation multiplication = (v1, v2) -> v1 * v2 + 0f;
        System.out.println(multiplication.operate(5, 3));


    }
}

