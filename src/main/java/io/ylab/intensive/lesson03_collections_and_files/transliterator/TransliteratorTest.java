package io.ylab.intensive.lesson03_collections_and_files.transliterator;

public class TransliteratorTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        String res = transliterator.
                transliterate("HELLO! ПРИВЕТ! Go, boy! ПОЙДЕМ, ПАРЕНЬ");
        System.out.println(res);
    }
}
