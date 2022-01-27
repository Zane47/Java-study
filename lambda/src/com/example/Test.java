package com.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> strs = Arrays.asList("Peter", "James", "anna", "jhon");

        // 集合排序
        Collections.sort(strs, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });

        // lambda
        Collections.sort(strs, (o1, o2) -> o2.compareTo(o1));
    }
}
