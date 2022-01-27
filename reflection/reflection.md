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

Class类, Constructor构造方法类, Method方法类, Field成员变量类

### Class类

Class对象: 描述其他类的对象

* Class是JVM中代表"类和接口"的类, 既然是类, 那么是可以实例化的

* Class对象具体包含了某个特定类的结构信息. (成员变量, 构造函数, 具体方法...)
* 过Class对象可获取对应类的构造方法/方法/成员变量

#### class的核心方法

| 方法                      | 用途                                             |
| ------------------------- | ------------------------------------------------ |
| Class.forName()           | 静态方法, 将指定的类加载到jvm并获取指定Class对象 |
| classObj.newInstance()    | 通过**默认构造方法**创建新的对象                 |
| classObj.getConstructor() | 获得指定的**public**修饰构造方法Constructor对象  |
| classObj.getMethod()      | 获取指定的**public**修饰方法Method对象           |
| classObj.getField()       | 获取指定的**public**修饰成员变量Field对象        |

#### class Example

首先定义Employee类

```java
public /*abstract*/ class Employee {
    // 验证: Class.forName()方法将指定的类加载到jvm,并返回对应Class对象
    static {
        System.out.println("Employee类已被加载到jvm, 并已初始化");
    }

    private Integer eno;
    private String ename;
    private Float salary;
    private String dname;

    public Employee() {
        System.out.println("Employee默认构造方法已被执行");
    }

    public Employee(Integer eno, String ename, Float salary, String dname) {
        this.eno = eno;
        this.ename = ename;
        this.salary = salary;
        this.dname = dname;
        System.out.println("Employee带参构造方法已被执行");
    }

    @Override
    public String toString() {
        return "Employee{" +
                "eno=" + eno +
                ", ename='" + ename + '\'' +
                ", salary=" + salary +
                ", dname='" + dname + '\'' +
                '}';
    }

    public Employee updateSalary(Float val) {
        this.salary = this.salary + val;
        System.out.println(this.ename + "调薪至" + this.salary + "元");
        return this;
    }
}
```

然后ClassExample

```java
public class ClassSample {
    public static void main(String[] args) {
        try {
            // Class.forName()方法将指定的类加载到jvm,并返回对应Class对象
            Class<?> employeeClass = Class.forName("com.example.reflect.entity.Employee");
            System.out.println("Employee已被加载到jvm");
            //newInstance通过默认构造方法创建新的对象
            Employee emp = (Employee)employeeClass.newInstance();
            emp.setDname("fff");
            System.out.println(emp);
        } catch (ClassNotFoundException e) {
            // 类名与类路径书写错误是抛出"类无法找到"异常
            // Class.forName("cim.example.reflect.entity.Employee");
            e.printStackTrace();
        } catch (InstantiationException e) {
            // 非法访问异常, 当在作用域外访问对象方法或成员变量时抛出
            // 默认构造方法改为private
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // 对象无法被实例化, 抛出"实例化异常"
            // abstract
            e.printStackTrace();
        }
    }
}
```

输出:

```
Employee类已被加载到jvm, 并已初始化
Employee已被加载到jvm
Employee默认构造方法已被执行
Employee{eno=null, ename='null', salary=null, dname='fff'}
```

### Constructor构造方法类

* Constructor类是对Java类中的构造方法的抽象
* Contructor对象包含了具体类的某个具体构造方法的声明

* 通过Constructor对象调用带参构造方法创建对象

#### 核心方法

| 方法                         | 用途                             |
| ---------------------------- | -------------------------------- |
| classObj.getConstructor()    | 获取指定public修饰的构造方法对象 |
| constructorObj.newlnstance() | 通过对应的构造方法创建对象       |

可以看到这里根据传参的不用构造对应的构造方法

#### Sample

```java
public class ConstructorSample {
    public static void main(String[] args) {
        try {
            Class<?> employeeClass = Class.forName("com.example.reflect.entity.Employee");

            Constructor<?> constructor = employeeClass.getConstructor(Integer.class, String.class, Float.class, String.class);

            Employee employee =
                    (Employee)constructor.newInstance(100, "lilei", 3000f, "dev");
            /*Constructor constructor = employeeClass.getConstructor(new Class[]{
                    Integer.class,String.class,Float.class,String.class
            });
            Employee employee = (Employee) constructor.newInstance(new Object[]{
                    100,"李磊",3000f,"研发部"
            });*/
           
            System.out.println(employee);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // 没有找到与之对应格式的方法. 参数类型或者数量不一致
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // 当被调用的方法的内部抛出了异常而没有被捕获时
            e.printStackTrace();
        }
    }
}
```

```
Employee类已被加载到jvm, 并已初始化
Employee带参构造方法已被执行
Employee{eno=100, ename='lilei', salary=3000.0, dname='dev'}
```

### Method方法类

* Method对象指代某个类中的方法的描述

* Method对象使用classObj.getMethod()方法获取

* `通过Method对象调用指定对象的对应方法

#### 核心方法

| 方法                 | 用途                             |
| -------------------- | -------------------------------- |
| classObj.getMethod() | 获取指定**public**修饰的方法对象 |
| methodObj.invoke()   | 调用指定对象的对应方法           |

#### Sample

```java
package com.example.reflect;


import com.example.reflect.entity.Employee;

import javax.lang.model.element.VariableElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 利用Method方法类调用
 */
public class MethodSample {
    public static void main(String[] args) {
        try {
            Class<?> employeeClass = Class.forName("com.example.reflect.entity.Employee");
            Constructor<?> constructor = employeeClass.getConstructor(Integer.class, String.class, Float.class, String.class);
            Employee employee = (Employee) constructor.newInstance(100, "lilei", 3000f, "dev");
            
            Method updateSalaryMethod = employeeClass.getMethod("updateSalary", Float.class);
            Employee employee1 = (Employee) updateSalaryMethod.invoke(employee, 1000f);
            System.out.println(employee1);


            Method updateSalary = Employee.class.getMethod("updateSalary", Float.class);
            Employee invoke = (Employee) updateSalary.invoke(employee, 50000f);
            System.out.println(invoke);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

```

```
Employee类已被加载到jvm, 并已初始化
Employee带参构造方法已被执行
lilei调薪至4000.0元
Employee{eno=100, ename='lilei', salary=4000.0, dname='dev'}
lilei调薪至54000.0元
Employee{eno=100, ename='lilei', salary=54000.0, dname='dev'}
```

### Field成员变量类

* Field对应某个具体类中的成员变量的声明

* Field对象使用classObj.getField()方法获取

* 在运行时, 通过Field对象可为某对象成员变量赋值/取值

#### 核心方法

| 方法                | 用途                                 |
| ------------------- | ------------------------------------ |
| classObj.getField() | 获取指定**public**修饰的成员变量对象 |
| fieldObj.set()      | 为某对象指定成员变量赋值             |
| fieldObj.get()      | 获取某对象指定成员变量数值           |

#### Sample

```java
/**
 * 利用Field对成员变量赋值/取值
 */
public class FieldSample {
    public static void main(String[] args) {
        try {
            Class<?> employeeClass = Class.forName("com.example.reflect.entity.Employee");
            Constructor<?> constructor =
                    employeeClass.getConstructor(Integer.class, String.class, Float.class, String.class);
            Employee employee = (Employee) constructor.newInstance(
                    100, "李磊", 3000f, "研发部");

            Field enameField = employeeClass.getField("ename");
            System.out.println((String) enameField.get(employee));

            enameField.set(employee, "李雷");
            String ename = (String) enameField.get(employee);

            System.out.println("ename:" + ename);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // 没有找到对应成员变量时抛出的异常
            // 只能找到public的成员变量
            e.printStackTrace();
        }
    }
}
```

classObj.getField():获取指定**public**修饰的成员变量对象, 如果是private的就会报错

```
java.lang.NoSuchFieldException: ename
```

所以把Employee的ename属性修改为public

```java
private Integer eno;
public String ename;
private Float salary;
private String dname;
```

就可以修改

```
Employee类已被加载到jvm, 并已初始化
Employee带参构造方法已被执行
李磊
ename:李雷
```

以上都需要访问public的方法, 那么如果要访问私有的, 如何?

## getDeclared系列方法

* getDeclaredConstructor(s)|Method(s)|Field(s)获取对应对象
* getConstructor(s)|Method(s)|Field(s)只能获取public对象
* 访问非public作用域内构造方法, 方法, 成员变量，会抛出异常

---

遇到public直接获取, 但是遇到private的, 就使用getter和setter

```java
/**
 * 获取对象所有成员变量值
 */
public class getDeclaredSample {
    public static void main(String[] args) {
        try {
            Class<?> employeeClass = Class.forName("com.example.reflect.entity.Employee");
            Constructor<?> constructor =
                    employeeClass.getConstructor(Integer.class, String.class, Float.class, String.class);

            Employee employee = (Employee) constructor.newInstance(new Object[]{
                    100, "李磊", 3000f, "研发部"
            });

            // 获取当前类所有成员变量
            Field[] fields = employeeClass.getDeclaredFields();
            for (Field field : fields) {
               // System.out.println(field.getName());
                if (field.getModifiers() == 1) {
                    // pubilc修饰
                    Object val = field.get(employee);
                    System.out.println(field.getName() + ":" + val);
                } else if (field.getModifiers() == 2) {
                    // private修饰
                    String methodName = "get" + field.getName().substring(0, 1).toUpperCase()
                            + field.getName().substring(1);
                    Method getMethod = employeeClass.getMethod(methodName);
                    // 不同的成员变量类型不一样, 不要强制转换
                    Object ret = getMethod.invoke(employee);
                    System.out.println(field.getName() + ":" + ret);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
```

```
Employee类已被加载到jvm, 并已初始化
Employee带参构造方法已被执行
eno:100
ename:李磊
salary:3000.0
dname:研发部
```

## 反射在项目中的使用

**在运行时**, 对类的成员变量和方法访问调用

国际化案例:

如果增加其他的语言, 不需要修改代码, 只需要增加对应的接口实现即可

```java
package com.example.i18n;

public interface I18N {
    public String say();
}
```

```java
package com.example.i18n;

public class En implements I18N {
    @Override
    public String say() {
        return "Cease to the struggle and cease to the life";
    }
}
```

```java
package com.example.i18n;

public class Zh implements I18N {
    @Override
    public String say() {
        return "生命不息奋斗不止";
    }
}
```

```java
package com.example.i18n;

import java.io.FileInputStream;

import java.net.URLDecoder;
import java.util.Properties;

public class Application {
    public static void main(String[] args) {
        Application.say();
    }

    public static void say() {
        Properties properties = new Properties();
        String configPath = Application.class.getResource("/config.properties").getPath();
        try {
            configPath = URLDecoder.decode(configPath, "UTF-8");
            properties.load(new FileInputStream(configPath));

            // 国际化实现类
            String language = properties.getProperty("language");

            I18N i18n = (I18N) Class.forName(language).newInstance();
            System.out.println(i18n.say());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

```properties
language=com.example.i18n.En
```




























