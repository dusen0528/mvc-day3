package com.nhnacademy.day3.stopwatch;

import java.lang.reflect.Method;
import java.util.Objects;

public class ArrayListTestProxy implements PerformanceTestable {

    // 다른 테스트 가능한 객체를 감싸서 실행시간을 측정해주는 래퍼(포장) 클래스
    private final PerformanceTestable performanceTestable; // 실제 테스트할 객체

    public ArrayListTestProxy(PerformanceTestable performanceTestable){
        this.performanceTestable = performanceTestable;
    }


    @Override
    public void test() {
        if(hasStopWatch()){ // StopWatch 어노테이션이 있는지 확인
            long start = System.currentTimeMillis();
            System.out.println("## 시간 측정 시작 : "+start);
            performanceTestable.test();
            long end = System.currentTimeMillis();
            System.out.println("## 시간 측정 종료 :" + end);

            long result = (end-start)/1000;
            System.out.println("실행시간(초):"+result);

        }
    }

    private boolean hasStopWatch() {
        // 클래스의 모든 메서드를 검사해서
        for(Method method:performanceTestable.getClass().getDeclaredMethods()){
            // @StopWatch 어노테이션이 있는지 확인
            StopWatch stopWatch = method.getAnnotation(StopWatch.class);
            if(Objects.nonNull(stopWatch)){ // 어노테이션이 있으면 true 반환
                return true;
            }
        }

        return false;
    }
}
