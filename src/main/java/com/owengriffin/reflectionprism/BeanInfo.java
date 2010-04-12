package com.owengriffin.reflectionprism;

/**
 * Replacement for java.lang.BeanInfo.
 *
 * @author Owen Griffin
 */
public class BeanInfo {

    private PropertyDescriptor[] propertyDescriptors;

    public void setPropertyDescriptors(PropertyDescriptor[] propertyDescriptors) {
        this.propertyDescriptors = propertyDescriptors;
    }

    public PropertyDescriptor[] getPropertyDescriptors() {
        return this.propertyDescriptors;
    }
}
