import java.util.*;

/**
 * A simple generic LRU (least-recently-used) cache.
 *
 * Expected behavior:
 *  - capacity must be > 0, otherwise throw IllegalArgumentException
 *  - put(k, v) inserts or updates; updating an existing key makes it most-recent
 *  - get(k) returns the value (or null if absent) and makes the key most-recent
 *  - when size would exceed capacity, evict the least-recently-used key
 *  - remove(k) deletes the key and returns its old value (null if absent)
 *  - keysInOrder() returns keys from least- to most-recently used,
 *    as a copy the caller cannot use to modify the cache
 *
 * This class has several planted bugs. Make all tests in LruCacheTest pass.
 */
public class LruCache<K, V> {

    private final int capacity;
    private final Map<K, V> values = new HashMap<>();
    private final List<K> order = new ArrayList<>(); // front = least recent

    public LruCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
    }

    public V get(K key) {
        if (!values.containsKey(key)) {
            return null;
        }
        order.remove(key);
        order.add(key);
        return values.get(key);
    }

    public void put(K key, V value) {
        if (values.containsKey(key)) {
            values.remove(key);
            order.remove(key);
        }
        values.put(key, value);
        order.add(key);
        System.out.println(values.size());
        if (values.size() > capacity) { //we evict only when size of cache exceeds capacity
            K eldest = order.remove(0);
            values.remove(eldest);
        }
    }

    public V remove(K key) {
        if (!values.containsKey(key)) {
            return null;
        }
        V old = values.remove(key);
        order.remove(key);
        return old;
    }

    public int size() {
        return values.size();
    }

    public List<K> keysInOrder() {
        return new ArrayList<>(order);
    }
}
