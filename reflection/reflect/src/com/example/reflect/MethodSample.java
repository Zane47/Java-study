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
