package core.utils;

import core.helpers.CacheHelper;

public class Hooks {
    public static void startCacheContext() {
        CacheHelper.startCacheContext();
    }

    public static void destroyCacheContext() {
        CacheHelper.destroyCacheContext();
    }

}
