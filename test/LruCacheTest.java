import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class LruCacheTest {

    private LruCache<String, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new LruCache<>(3);
    }

    @Test
    void holdsUpToCapacity() { //pass
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        assertEquals(3, cache.size());
        assertEquals(1, cache.get("a"));
    }

    @Test
    void evictsLeastRecentlyUsed() { //pass 
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        cache.put("d", 4);
        assertNull(cache.get("a"));
        assertEquals(List.of("b", "c", "d"), cache.keysInOrder());
    }

    @Test
    void getRefreshesRecency() { //pass
        cache.put("a", 1);
        cache.put("b", 2);
        cache.put("c", 3);
        cache.get("a");
        cache.put("d", 4);
        assertEquals(1, cache.get("a"));
        assertNull(cache.get("b"));
    }

    @Test
    void updateDoesNotDuplicateKey() { //pass
        cache.put("a", 1);
        cache.put("a", 10);
        assertEquals(1, cache.size());
        assertEquals(List.of("a"), cache.keysInOrder());
        assertEquals(10, cache.get("a"));
    }

    @Test
    void removeClearsKeyFromOrder() { //pass
        cache.put("a", 1);
        cache.put("b", 2);
        assertEquals(1, cache.remove("a"));
        assertEquals(List.of("b"), cache.keysInOrder());
    }

    @Test
    void removeWorksForNullValues() { //pass
        LruCache<String, Integer> c = new LruCache<>(2);
        c.put("x", null);
        c.remove("x");
        assertEquals(0, c.size());
        assertTrue(c.keysInOrder().isEmpty());
    }

    @Test
    void rejectsInvalidCapacity() {//pass
        assertThrows(IllegalArgumentException.class, () -> new LruCache<String, Integer>(0));
        assertThrows(IllegalArgumentException.class, () -> new LruCache<String, Integer>(-1));
    }

    @Test
    void keysInOrderIsDefensiveCopy() { 
        cache.put("a", 1);
        List<String> keys = cache.keysInOrder();
        try {
            keys.clear();
        } catch (UnsupportedOperationException ignored) {
        }
        assertEquals(List.of("a"), cache.keysInOrder());
    }
}
