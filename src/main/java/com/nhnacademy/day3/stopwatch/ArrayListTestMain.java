package com.nhnacademy.day3.stopwatch;

public class ArrayListTestMain {
    public static void main(String[] args) {

        System.out.println("===ArrayList 테스트===");
        ArrayListTest arrayListTest = new ArrayListTest();
        ArrayListTestProxy arrayListTestProxy = new ArrayListTestProxy(arrayListTest);
        arrayListTestProxy.test();

        System.out.println("===LinkedList 테스트===");
        LinkedListTest linkedListTest = new LinkedListTest();
        ArrayListTestProxy linkedListProxy = new ArrayListTestProxy(linkedListTest);
        linkedListProxy.test();
    }
}
