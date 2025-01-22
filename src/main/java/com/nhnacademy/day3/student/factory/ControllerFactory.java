package com.nhnacademy.day3.student.factory;

import com.nhnacademy.day3.student.annotation.RequestMapping;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.Set;

public class ControllerFactory {
    private final ConcurrentMap<String, Object> beanMap = new ConcurrentHashMap<>();

    public void init(Set<Class<?>> c) {
        for (Class<?> clazz : c) {
            RequestMapping annotation = clazz.getAnnotation(RequestMapping.class);
            if (annotation != null) {
                String key = annotation.method() + ":" + annotation.value();
                try {
                    Object command = clazz.getDeclaredConstructor().newInstance();
                    beanMap.put(key, command);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public Object getBean(String method, String path) {
        return beanMap.get(method + ":" + path);
    }
}