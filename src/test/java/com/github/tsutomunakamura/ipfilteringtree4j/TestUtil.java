package com.github.tsutomunakamura.ipfilteringtree4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Field;

public class TestUtil {
    private TestUtil() {}

    public static <E> Object invokeInstanceMethod(E instance, String methodName, Class[] types, Object... args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethod(instance, (Class<E>)instance.getClass(), methodName, types, args);
    }
    public static <E> Object invokeStaticMethod(Class<E> c, String methodName, Class[] types, Object... args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return invokeMethod(null, c, methodName, types, args);
    }
    public static <E> Method getMethod(Class<E> clazz, String methodName, Class [] types) throws NoSuchMethodException {
        Method m = clazz.getDeclaredMethod(methodName, types);
        m.setAccessible(true);
        return m;
    }
    private static <E> Object invokeMethod(E instance, Class<E> c, String methodName, Class[] types, Object... args)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method method = getMethod(c, methodName, types);
        if(instance == null)
            return method.invoke(args);
        return method.invoke(instance, args);
    }

    public static <E> Object getInstanceField(E instance, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        return getField(instance, (Class<E>)instance.getClass(), fieldName);
    }

    public static <E> Object getStaticField(Class<E> clazz, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        return getField(null, clazz, fieldName);
    }

    private static <E> Object getField(E instance, Class<E> clazz, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);

        return f.get(instance);
    }
}
