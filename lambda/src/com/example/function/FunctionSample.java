package com.example.function;

import java.util.Random;
import java.util.function.Function;

/**
 * 利用Function函数式接口生成定长的随机字符串
 */
public class FunctionSample {
    public static void main(String[] args) {
        Function<Integer, String> randomStr = len -> {
            String lib = "0123456789qwertyuiopasdfghjklzxcvbnm";
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < len; i++) {
                Random random = new Random();
                int r = random.nextInt(lib.length());
                sb.append(lib.charAt(r));
            }
            return sb.toString();
        };
        System.out.println(randomStr.apply(15));
    }



}
