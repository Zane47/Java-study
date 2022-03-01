# Lambda

* 实例讲解Lambda语法
* 基于Lambda实现函数式编程
* Stream流式处理

## 什么是Lambda表达式

* JDK8开始支持Lambda表达式，用来让程序编写更优雅
* 利用Lambda可以更简洁的实现匿名内部类与函数声明与调用
* 基于Lambda提供stream流式处理极大简化对集合的操作

传统代码的集合排序和Lambda排序

```java
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
```

## Lambda语法

(参数列表) -> {实现语句}

参数列表: 使用逗号分隔参数; 参数类型可省略; 单参数括号可省略

实现语句: 单行直接写; 多行用{}包括

---

学习代码, 以MathOperation为例子:

定义接口:

````java
package com.example.math;

/**
 * 四则运算接口
 */
public interface MathOperation {
    public Float operate(Integer v1, Integer v2);
}

````

思考一个问题: 如果实现接口采用加法运算,  

-> 创建一个新的类实现接口, 然后在operate方法中进行加法返回.

```java
class Addition implements MathOperation {
    @Override
    public Float operate(Integer v1, Integer v2) {
        System.out.println("addition operation");
        return v1 + v2 + 0f;
    }
}

Addition addition = new Addition();
addition.opearte(5, 3);
```

-> 如果使用lambda表达式, 那么创建新类的行为就可以省略, 直接使用lambda表达式 -> 注意lambda有多种使用方式

新建类:

```java
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
```

需要注意的是:

> Lambda表达式只能实现有且只有一个抽象方法的接口，Java称之为"函数式接口"

如果接口中有多个方法, 则会报错:
`Multiple non-overriding abstract methods found in interface com.imooc.lambda.MathOperation`











































## 函数式编程













## Stream









