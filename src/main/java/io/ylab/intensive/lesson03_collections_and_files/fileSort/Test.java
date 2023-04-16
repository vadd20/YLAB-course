package io.ylab.intensive.lesson03_collections_and_files.fileSort;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        File dataFile = new Generator().generate("./src/main/java/io/ylab/intensive/lesson03_collections_and_files/fileSort/data.txt", 800);
        System.out.println("Before sort:");
        System.out.println("Result " + new Validator(dataFile).isSorted()); // false
        File sortedFile = new Sorter().sortFile(dataFile);
        System.out.println("After sort:");
        System.out.println("Result " + new Validator(sortedFile).isSorted()); // true
    }
}
