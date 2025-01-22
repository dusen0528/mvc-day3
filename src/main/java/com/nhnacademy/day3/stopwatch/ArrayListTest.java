package com.nhnacademy.day3.stopwatch;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest implements  PerformanceTestable{

    @StopWatch
    @Override
    public void test() {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            integerList.add(i);
        }
    }
}
