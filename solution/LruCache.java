import java.util.*;

// REFERENCE FIX - only look after you've tried!
// Bugs fixed:
//  1. constructor: no validation -> throw IllegalArgumentException if capacity <= 0
//  2. get(): didn't refresh recency -> move key to the back of the order list
//  3. put(): updating an existing key added a duplicate to order -> remove it first
//  4. put(): off-by-one, ">=" evicted at capacity -> evict only when size > capacity
//  5. remove(): "old != null" check skipped keys whose value is null -> use containsKey
//  6. keysInOrder(): leaked the internal mutable list -> return a copy
// (Alternative idiomatic solution: extend LinkedHashMap with accessOrder=true
//  and override removeEldestEntry.)
public class LruCache<K, V> {

    private final int capacity;
    private final Map<K, V> values = new HashMap<>();
    private final List<K> order = new ArrayList<>();

    public LruCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("capacity must be > 0");
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
            order.remove(key);
        }
        values.put(key, value);
        order.add(key);
        if (values.size() > capacity) {
            K eldest = order.remove(0);
            values.remove(eldest);
        }
    }

    public V remove(K key) {
        if (!values.containsKey(key)) {
            return null;
        }
        order.remove(key);
        return values.remove(key);
    }

    public int size() {
        return values.size();
    }

    public List<K> keysInOrder() {
        return new ArrayList<>(order);
    }
}
