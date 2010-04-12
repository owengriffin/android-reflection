package com.owengriffin.reflectionprism;

import java.lang.reflect.Method;

/**
 * Reflection for java.lang.PropertyDescriptor
 *
 * @author Owen Griffin
 */
public class PropertyDescriptor {

    private String name;
    private Method readMethod;
    private Method writeMethod;

    public PropertyDescriptor(String name, Method read, Method write) {
        this.name = name.toLowerCase();
        this.readMethod = read;
        this.writeMethod = write;
    }

    public Method getReadMethod() {
        return this.readMethod;
    }

    public Method getWriteMethod() {
        return this.writeMethod;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        String type = this.writeMethod.getParameterTypes()[0].getName();
        return this.getClass().getName() + " [name = " + this.name + " read = " + this.readMethod.getName() + " write =" + this.writeMethod.getName() + " type = " + type + "]";
    }
}

