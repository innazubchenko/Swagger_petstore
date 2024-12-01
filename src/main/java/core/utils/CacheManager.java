package core.utils;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {
    private static final InheritableThreadLocal<Map<String, Object>> projectContext = new InheritableThreadLocal<>();

    public static void startCacheContext() {
        projectContext.set(new HashMap<>());
    }

    public static void destroyCacheContext() {
        projectContext.remove();
    }

    public static Object getValue(Enum<?> key) {
        return getValue(key.toString());
    }

    public static Object getValue(String key) {
        return projectContext.get().get(key);
    }

    public static void setValue(Enum<?> key, Object value) {
        setValue(key.toString(), value);
    }

    public static void setValue(String key, Object value) {
        projectContext.get().put(key, value);
    }

}
