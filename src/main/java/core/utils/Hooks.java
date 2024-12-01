package core.utils;

public class Hooks {
    public static void startCacheContext() {
        CacheManager.startCacheContext();
    }

    public static void destroyCacheContext() {
        CacheManager.destroyCacheContext();
    }

}
