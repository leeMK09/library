package com.work.library.utils;

import java.util.Collection;

public class CollectionUtils {
    public static boolean isEqualsSize(Collection<?> list1, Collection<?> list2) {
        return getDistinctCount(list1) == getDistinctCount(list2);
    }

    private static long getDistinctCount(Collection<?> list) {
        return list.stream().distinct().count();
    }
}
