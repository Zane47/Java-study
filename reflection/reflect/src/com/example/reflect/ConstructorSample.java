package com.example.reflect;

import com.example.reflect.entity.Employee;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
