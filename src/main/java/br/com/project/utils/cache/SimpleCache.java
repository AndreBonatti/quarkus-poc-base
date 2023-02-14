package br.com.project.utils.cache;

import java.util.List;

public interface SimpleCache<K, V> {
    void addEntry(K key, V value);

    boolean hasObjects();

    List<V> getAll();
}
