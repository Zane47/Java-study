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























## 函数式编程













## Stream









