package com.example.reflect;


import com.example.reflect.entity.Employee;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

            //获取当前类所有成员变量
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

