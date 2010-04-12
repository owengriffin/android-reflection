package com.owengriffin.reflectionprism;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

    public static boolean isWriteable(Object target, String name) {
        logger.info("isWriteable (target = " + target + ", name = " + name + ")");
        PropertyDescriptor descriptor = getPropertyDescriptor(target, name);
        boolean retval = descriptor.getWriteMethod() != null;
        logger.info("retval = " + retval);
        return retval;
    }

    public static Class getPropertyType(Object target, String name) {
        logger.info("getPropertyType (target = " + target + ", name = " + name + ")");
        PropertyDescriptor descriptor = getPropertyDescriptor(target, name);
        Class retval = descriptor.getWriteMethod().getParameterTypes()[0];
        logger.info("retval = " + retval);
        return retval;
    }

    public static void setProperty(Object target, String name, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        logger.info("setProperty (target = " + target + ", name = " + name + ", value = " + value + ")");
        PropertyDescriptor descriptor = getPropertyDescriptor(target, name);
        descriptor.getWriteMethod().invoke(target, new Object[]{value});
    }

    public static PropertyDescriptor getPropertyDescriptor(Object target, String name) {
        List<PropertyDescriptor> descriptors = getPropertyDescriptors(target.getClass());
        for (PropertyDescriptor descriptor : descriptors) {
            if (descriptor.getName().equals(name)) {
                //logger.debug("Returning property descriptor " + descriptor);
                return descriptor;
            }
        }
        return null;
    }

    public static List<PropertyDescriptor> getPropertyDescriptors(Class<? extends Object> clazz) {
        List<Method> methods = accessors(clazz);
        Set<String> properties = new HashSet<String>();
        for (Method method : methods) {
            properties.add(method.getName().substring(3));
        }

        logger.debug("properties = " + properties);

        List<PropertyDescriptor> propertyDescriptors = new ArrayList<PropertyDescriptor>();
        for (String property : properties) {
            Method readMethod = getMethodWithPrefix(methods, "get", property);
            Method writeMethod = getMethodWithPrefix(methods, "set", property);
            PropertyDescriptor descriptor = new PropertyDescriptor(property, readMethod, writeMethod);
            propertyDescriptors.add(descriptor);
        }
        return propertyDescriptors;
    }

    public static boolean isReadable(Object bean, String propertyName) {
        logger.info("isReadable (target = " + bean + ", name = " + propertyName + ")");
        return getPropertyDescriptor(bean, propertyName).getReadMethod() != null;
    }

    public static Object getProperty(Object bean, String propertyName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return getPropertyDescriptor(bean, propertyName).getReadMethod().invoke(bean, new Object[]{});
    }

    /**
     * Return a list of methods from the class which have the specified prefix.
     */
    @SuppressWarnings("unchecked")
    private static List<Method> accessors(Class clazz) {
        List<Method> retval = new ArrayList<Method>();
        for (Method method : clazz.getMethods()) {
            if (!method.getName().endsWith("Class") && !(method.getName().length() == 11 && method.getName().endsWith("Property"))) {
                if (method.getName().startsWith("get") || method.getName().startsWith("set")) {
                    retval.add(method);
                }
            }
        }
        return retval;
    }

    private static Method getMethodWithPrefix(List<Method> methods, String prefix, String name) {
        for (Method method : methods) {
            if (method.getName().equals(prefix + name)) {
                return method;
            }
        }
        return null;
    }
}
