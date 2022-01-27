package com.example.reflect;


import com.example.reflect.entity.Employee;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

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
