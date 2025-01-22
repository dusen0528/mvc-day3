package com.nhnacademy.day3.user;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public final class InjectUtil {

    public static <T> T getObject(Class<T> classType) {
        // 1. 클래스를 받아 Object 생성
        T instance = createInstance(classType);
        // 2. 클래스의 부품(Fields)들을 확인
        Field[] fields = classType.getDeclaredFields();
        for (Field field : fields) {
            // 3. 특별한 표시 @Autowired 가 있는 필드 찾기
            if (field.getAnnotation(Autowired.class) != null) {
                // 4. 필요한 필드들을 만들어 끼워 넣는 과정
                Object fieldInstance = createInstance(field.getType());
                field.setAccessible(true);
                try {
                    field.set(instance, fieldInstance);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return instance;
    }

    private static <T> T createInstance(Class<T> classType) {
        try {
            return classType.getConstructor(null).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}