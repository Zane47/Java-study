# 反射Reflect

* 介绍反射及作用

* 讲解反射四个核心类

* 反射在项目中的应用

---

## 什么是反射

* 反射（Reflect）是在**运行时**动态访问类与对象的技术. 

* 反射是JDK1.2版本后的高级特性，隶属于**java.lang.reflect**
* 大多数Java框架都基于反射实现参数配置, 动态注入等特性

以前实例化对象使用new, 实例化工作写死在程序中. 不够灵活.

反射的根本目的是把对象的创建时机从原先的编译时, 延迟到程序运行时

---

例子: Math

四则运算接口:

```java
package com.imooc.reflect;

/**
 * 四则运算接口
 */
public interface MathOperation {
    public float operate(int a, int b);
}
```

impl

```java
package com.imooc.reflect;

public class Addition implements MathOperation {
    @Override
    public float operate(int a, int b) {
        System.out.println("operate addition");
        return a + b;
    }
}
```

```java
package com.imooc.reflect;

public class Subtraction implements MathOperation {
    @Override
    public float operate(int a, int b) {
        System.out.println("operate subtraction");
        return a - b;
    }
}
```

main

```java
package com.imooc.reflect;

import java.util.Scanner;

/**
 * 初识反射的作用
 */
public class ReflectSample {

    public static void main(String[] args) {
        ReflectSample.case1();
    }

    /**
     * 传统的创建对象方式
     */
    public static void case1() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入计算类名:");
        String op = scanner.next();
        System.out.print("请输入a:");
        int a = scanner.nextInt();
        System.out.print("请输入b:");
        int b = scanner.nextInt();


        MathOperation mathOperation = null;
        if (op.equals("Addition")) {
            mathOperation = new Addition();
        } else if (op.equals("Subtraction")) {
            mathOperation = new Subtraction();
        } else if (op.equals("Multiplication")) {
            // mathOperation = new Multiplication();
        } else {
            System.out.println("无效的计算类");
            return;
        }
        float result = mathOperation.operate(a, b);
        System.out.println(result);
    }
}
```

如果这个时候需要拓展乘法和除法, 那么又需要硬编码的形式来修改源代码代码. 

-> 所以这里使用使用反射技术动态创建对象, 更加灵活

````java
/**
     * 利用反射创建对象更加灵活
     */
public static void case2() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("请输入计算类名:");
    String op = scanner.next();
    System.out.print("请输入a:");
    int a = scanner.nextInt();
    System.out.print("请输入b:");
    int b = scanner.nextInt();
    MathOperation mathOperation = null;
    try {
        mathOperation = (MathOperation) Class.forName("com.imooc.reflect." + op).newInstance();
    } catch (Exception e) {
        System.out.println("无效的计算类");
        return;
    }
    float result = mathOperation.operate(a, b);
    System.out.println(result);
}
````

Class.forName: 用于加载指定的类

newInstance(): 对类进行实例化

因为都实现了接口中的方法, 所以返回的时候声明为接口即可

---

使用反射技术的好处:

如果要增加乘法运算, 首先要增加乘法的impl, 

```java
package com.imooc.reflect;

public class Multiplication implements MathOperation {
    @Override
    public float operate(int a, int b) {
        return a * b;
    }
}
```

那么在case1的传统方式中, 就需要增加elseif的情况

```java
public static void case1() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("请输入计算类名:");
    String op = scanner.next();
    System.out.print("请输入a:");
    int a = scanner.nextInt();
    System.out.print("请输入b:");
    int b = scanner.nextInt();


    MathOperation mathOperation = null;
    if (op.equals("Addition")) {
        mathOperation = new Addition();
    } else if (op.equals("Subtraction")) {
        mathOperation = new Subtraction();
    } else if (op.equals("Multiplication")) {
        mathOperation = new Multiplication();
    } else {
        System.out.println("无效的计算类");
        return;
    }
    float result = mathOperation.operate(a, b);
    System.out.println(result);
}
```

都是提前写好了的, 需要额外的追加硬编码的形式. 

但是如果在企业应用中, 这就需要重新测试, 重新打包上线等等操作, 繁琐的过程.

-> 希望可以在不修改源代码的情况下, 动态生成, 

反射的方式, 额外的增加了乘法, 不需要额外增加硬编码. 

运行时加载, 看这一句`mathOperation = (MathOperation) Class.forName("com.imooc.reflect." + op).newInstance();`, 只有在运行的时候才知道要创建的是哪一个对象, 不是在编译时写死, 而是在运行时动态决定创建什么对象. 



## 反射核心类

4个反射核心类: 

Constructor构造方法类, Class类, Field成员变量类, Method方法类













