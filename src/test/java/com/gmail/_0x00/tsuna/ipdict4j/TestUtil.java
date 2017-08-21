package com.gmail._0x00.tsuna.ipdict4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestUtil {
    private TestUtil() {}

    public static <E> Object invokeInstanceMethod(E instance, String methodName, Object... args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethod(instance, (Class<E>)instance.getClass(), methodName, args);
    }
    public static <E> Object invokeStaticMethod(Class<E> c, String methodName, Object... args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethod(null, c, methodName, args);
    }
    private static <E> Object invokeMethod(E instance, Class<E> c, String methodName, Object... args)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = c.getDeclaredMethod(methodName);
        method.setAccessible(true);

        System.out.println("methodName: " + methodName);
        if(instance == null)
            return method.invoke(args);
        return method.invoke(instance, args);
    }
}
