package com.owengriffin.reflectionprism;

/**
 * Alias for org.apache.commons.beanutils.ConvertUtils.
 *
 * @author Owen Griffin
 */
public class ConvertUtils {

    public static Object convert(String string, Class targetClass) {
        return org.apache.commons.beanutils.ConvertUtils.convert(string, targetClass);
    }
}
