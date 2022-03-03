package com.example.consumer;

import java.util.function.Consumer;

/**
 * consumer接口使用
 */
public class ConsumerSample {

    public static void main(String[] args) {
        // console输出
        output(s -> System.out.println("console: " + s));

        // 网站输出(示意)
        output(s -> {
            // to web
            System.out.println("xxx website: " + s);
        });
    }

    private static void output(Consumer<String> consumer) {
        String text = "text";
        consumer.accept(text);
    }


}
