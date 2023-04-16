package io.ylab.intensive.lesson03_collections_and_files.datedMap;

public class DatedMapTests {
    public static void main(String[] args) throws InterruptedException {
        DatedMap datedMap = new DatedMapImpl();

        datedMap.put("first", "value1");
        Thread.sleep(2000);
        datedMap.put("second", "value2");
        Thread.sleep(1000);
        datedMap.put("third", "value3");
        datedMap.remove("third");

        System.out.println(datedMap.get("first"));
        System.out.println(datedMap.containsKey("second"));
        System.out.println(datedMap.keySet());
        System.out.println(datedMap.getKeyLastInsertionDate("first"));
        System.out.println(datedMap.getKeyLastInsertionDate("second"));
        System.out.println(datedMap.getKeyLastInsertionDate("third"));
    }
}
