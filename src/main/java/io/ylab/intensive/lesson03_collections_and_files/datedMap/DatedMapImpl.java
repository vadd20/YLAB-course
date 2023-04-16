package io.ylab.intensive.lesson03_collections_and_files.datedMap;

import java.util.Date;
import java.util.Set;
import java.util.HashMap;

public class DatedMapImpl implements DatedMap {
    private HashMap <String, String> datedMap = new HashMap<>();
    private HashMap<String, Date> datedHashMapWithDate = new HashMap<>();
    @Override
    public void put(String key, String value) {
        datedMap.put(key, value);
        datedHashMapWithDate.put(key, new Date());
    }

    @Override
    public String get(String key) {
        return datedMap.get(key);
    }

    @Override
    public boolean containsKey(String key) {
        return datedMap.containsKey(key);
    }

    @Override
    public void remove(String key) {
        datedMap.remove(key);
    }

    @Override
    public Set<String> keySet() {
        return datedMap.keySet();
    }

    @Override
    public Date getKeyLastInsertionDate(String key) {
        if (datedMap.containsKey(key)) {
            return datedHashMapWithDate.get(key);
        }
        return null;
    }


}
