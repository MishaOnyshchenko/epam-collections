package com.epam.lab.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;


public class HashMap<K, V> implements Map<K, V> {
    private int mapCapacity = 5;
    private int hashTableCapacity = 3 * mapCapacity / 2;

    private int[] hashTable = new int[hashTableCapacity];
    private int[] collisions = new int[mapCapacity];
    private Entry[] entries = new Entry[mapCapacity];
    private int currentCell = 1;

    public HashMap() {
    }

    public HashMap(Map<? extends K, ? extends V> m) {
        putMapEntries(m);
    }

    public int getMapCapacity() {
        return mapCapacity;
    }

    public int getHashTableCapacity() {
        return hashTableCapacity;
    }

    @Override
    public V get(K key) {
        int hash = hash(key);
        for (int index = hashTable[hash]; index != 0; index = collisions[index]) {
            if (Objects.equals(entries[index].key, key)) {
                return (V) entries[index].value;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        if (currentCell == mapCapacity) {
            resize();
        }
        Entry entry = new Entry(key, value);
        int hash = hash(key);

        for (int index = hashTable[hash]; index != 0; index = collisions[index]) {
            if (Objects.equals(entries[index].key, key)) {
                entries[index] = entry;
                return;
            }
        }
        entries[currentCell] = entry;
        collisions[currentCell] = hashTable[hash];
        hashTable[hash] = currentCell++;
    }

    public void putMapEntries(Map<? extends K, ? extends V> m) {
        HashMap map = (HashMap) m;
        for (int i = 1; i <= m.size(); i++) {
            K key = (K) map.entries[i].key;
            V value = (V) map.entries[i].value;
            put(key, value);
        }
    }

    public void resize() {
        mapCapacity *= 2;
        hashTableCapacity = 3 * mapCapacity / 2;
        entries = Arrays.copyOf(entries, mapCapacity);
        collisions = Arrays.copyOf(collisions, mapCapacity);
        hashTable = Arrays.copyOf(hashTable, hashTableCapacity);
    }

    public int hash(K key) {
        return Math.abs(key.hashCode()) % hashTableCapacity;
    }

    @Override
    public int size() {
        return currentCell - 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashMap)) return false;
        Iterator<Map.Entry<String, String>> e1 = iterator();
        Iterator<Map.Entry<String, String>> e2 = ((HashMap) o).iterator();
        while (e1.hasNext() && e2.hasNext()) {
            Entry o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1 == null ? o2 == null : o1.equals(o2))) {
                return false;
            }
        }
        return !(e1.hasNext() || e2.hasNext());
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (Entry e : this) {
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        }
        return hashCode;
    }

    @Override
    public Iterator iterator() {
        return new MapIterator();
    }

    public class MapIterator implements Iterator {
        int nextIndex = 1;

        @Override
        public boolean hasNext() {
            return nextIndex < currentCell;
        }

        @Override
        public Entry<K, V> next() {
            return entries[nextIndex++];
        }
    }
}