package io.ylab.intensive.lesson03_collections_and_files.datedMap;

import java.util.Date;
import java.util.Set;

public interface DatedMap {
    void put(String key, String value);
    String get(String key);
    boolean containsKey(String key);
    void remove(String key);
    Set<String> keySet();
    Date getKeyLastInsertionDate(String key);
}
