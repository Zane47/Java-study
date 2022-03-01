package com.example.predicate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class PredicateSample {

    public static void main(String[] args) {
        // ------------------------ 1. 简单应用 ------------------------
        Predicate<Integer> predicate = n -> n > 4;
        System.out.println(predicate.test(4));

        // ------------------------ 2. ------------------------
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // java写法. 代码写完之后发现只能判断奇数功能
        /*for (Integer num : list) {
            if (num % 2 == 1) {
                System.out.println(num);
            }
        }*/
        // predicate函数式编程
        filter(list, n -> n % 2 == 1);
    }


    public static void filter(List<Integer> list, Predicate<Integer> predicate) {
        for (Integer num : list) {
            if (predicate.test(num)) {
                System.out.print(num + " ");
            }
        }
    }


}
