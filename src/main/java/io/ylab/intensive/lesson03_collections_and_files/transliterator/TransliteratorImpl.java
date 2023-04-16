package io.ylab.intensive.lesson03_collections_and_files.transliterator;

import java.util.HashMap;

public class TransliteratorImpl implements Transliterator {
    @Override
    public String transliterate(String source) {
        HashMap <Character, String> vocabulary = new HashMap<>();
        vocabulary.put('А', "A");
        vocabulary.put('Б', "B");
        vocabulary.put('В', "V");
        vocabulary.put('Г', "G");
        vocabulary.put('Д', "D");
        vocabulary.put('Е', "E");
        vocabulary.put('Ё', "E");
        vocabulary.put('Ж', "ZH");
        vocabulary.put('З', "Z");
        vocabulary.put('И', "I");
        vocabulary.put('Й', "I");
        vocabulary.put('К', "K");
        vocabulary.put('Л', "L");
        vocabulary.put('М', "M");
        vocabulary.put('Н', "N");
        vocabulary.put('О', "O");
        vocabulary.put('П', "P");
        vocabulary.put('Р', "R");
        vocabulary.put('С', "S");
        vocabulary.put('Т', "T");
        vocabulary.put('У', "U");
        vocabulary.put('Ф', "F");
        vocabulary.put('Х', "KH");
        vocabulary.put('Ц', "TS");
        vocabulary.put('Ч', "CH");
        vocabulary.put('Ш', "SH");
        vocabulary.put('Щ', "SHCH");
        vocabulary.put('Ы', "Y");
        vocabulary.put('Ь', "");
        vocabulary.put('Ъ', "IE");
        vocabulary.put('Э', "E");
        vocabulary.put('Ю', "IU");
        vocabulary.put('Я', "IA");

        for (int i = 0; i < source.length(); i++) {
            char currentSymbol = source.charAt(i);
            if (vocabulary.containsKey(currentSymbol)) {
                source = source.replace(String.valueOf(currentSymbol), vocabulary.get(currentSymbol));
            }
        }
        return source;
    }
}
