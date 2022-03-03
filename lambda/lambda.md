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

[Package java.util.function](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html)

### 函数式编程和函数式接口

#### 函数式编程

* 函数式编程: 基于**函数式接口**并使用lambda表达的编程方式

函数式接口: 在一个接口中, 有且只有一个抽象方法的接口. 

* 函数式编程理念: 将代码作为可重用数据代入到程序运行中

以前的面向对象开发, 数据(人名, 学号)都是具体的信息. 但是在函数式编程中, 核心就是将之前准备好的代码作为一种可重用的资源参与到程序的运行过程中. 在程序运行过程中, 通过传入不同的代码块使程序产生不同的行为.

* 函数式编程强调"你想做什么", 而不是"你想怎么做"

灵活, 代码开发轻松. 代码直观, 不存在晦涩的函数和封装, 平铺直叙, 从上往下完成功能.

---

#### 函数式接口

* 函数式接口: 在一个接口中, 有且只有一个抽象方法的接口. 

* Java中拥有大量函数式接口, 如java.lang.Runnable. 用于创建线程使用的接口, 其中只有一个方法, run方法

* JDK8后提供了一系列新的函数式接口，位于**java.util.function**

---

### 函数式接口Predicat

* Predicate是新增的函数式接口,位于java.util.function

* Predicate用于测试传入的数据是否满足判断要求. 类似if判断.

* Predicate接口需要实现test(方法进行逻辑判断. 唯一方法test

```java
import java.util.function.Predicate;

public class PredicateSample {

    public static void main(String[] args) {

        Predicate<Integer> predicate = n -> n > 4;
        System.out.println(predicate.test(4));
    }
}
```

Predicate代码:

```java
package java.util.function;

import java.util.Objects;

/**
 * Represents a predicate (boolean-valued function) of one argument.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(Object)}.
 *
 * @param <T> the type of the input to the predicate
 *
 * @since 1.8
 */
@FunctionalInterface
public interface Predicate<T> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(T t);

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * AND of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code false}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * AND of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default Predicate<T> and(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) && other.test(t);
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    default Predicate<T> negate() {
        return (t) -> !test(t);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * OR of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code true}, then the {@code other}
     * predicate is not evaluated.
     *
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * OR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default Predicate<T> or(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) || other.test(t);
    }

    /**
     * Returns a predicate that tests if two arguments are equal according
     * to {@link Objects#equals(Object, Object)}.
     *
     * @param <T> the type of arguments to the predicate
     * @param targetRef the object reference with which to compare for equality,
     *               which may be {@code null}
     * @return a predicate that tests if two arguments are equal according
     * to {@link Objects#equals(Object, Object)}
     */
    static <T> Predicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : object -> targetRef.equals(object);
    }
}
```

其中使用了泛型T.

Predicate就是用来判断的函数式接口, 传入的数据代入方法实现中, 由具体的代码(例如上面的n>4)来查看返回true还是false.

---

那如何体现函数式编程的特点?

假设有list集合包含了一系列数字, 希望提取所有的奇数

```java
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
        for (Integer num : list) {
            if (num % 2 == 1) {
                System.out.println(num);
            }
        }
        // predicate函数式编程
       
    }
}
```

Java写法可以发现: 代码写完之后发现只能判断奇数功能. 如果要提取偶数就需要改变查询条件. 更好的方法 -> 函数式编程Predicate

```java
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
                System.out.print(num + " ")
            }
        }
    }
}
```

可以看到在filter方法中predicate到底实现了什么筛选条件此时是并不清楚的, 只有在程序运行的过程中, 由代码来决定他的判断条件. `filter(list, n -> n % 2 == 1);`

具体的判断条件不是在程序中完成的, 而是由外面的调用来实现.

---

函数式接口的典型实现: 以参数的方式传入到方法中. 传入的具体实现是灵活变化的

不是具体的数值, 而是将已有的代码作为可重入资源放到程序中. 灵活

### 函数式接口Consumer

Represents an operation that accepts a single input argument and returns no result.

在日常处理中只需要有一个参数输入, 不需要返回任何结果时使用.

| 接口            | 用途                                       |
| --------------- | ------------------------------------------ |
| ``Consumer<T>`` | 对应有一个输入参数无输出的功能代码         |
| `Function<T,R>` | 对应有一个输入参数且需要返回数据的功能代码 |
| `Predicate<T>`  | 用于条件判断，固定返回布尔值               |

Function<T,R>: T: 参数类型, R:返回类型

---

Consumer的使用示例:

text输出到多个平台可能, console, 文件, 网络传输等

```java
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
```

查看consumer源码可以看到, consumer就是函数式接口, 不需要return, 直接写函数实现即可.

Consumer: Represents an operation that accepts a single input argument and returns no

result.

```java
/**
 * Represents an operation that accepts a single input argument and returns no
 * result. Unlike most other functional interfaces, {@code Consumer} is expected
 * to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object)}.
 *
 * @param <T> the type of the input to the operation
 *
 * @since 1.8
 */
@FunctionalInterface
public interface Consumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t);

    /**
     * Returns a composed {@code Consumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code Consumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }


```

### 函数式接口Function

Function<T,R>: Represents a function that accepts one argument and produces a result.

T: 输入参数类型

R: 返回数据类型

Function: 一个参数且需要返回数据















## Stream









