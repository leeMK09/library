package com.work.library.utils;

import java.util.List;

public class CollectionUtils {
    public static <T> long getDistinctCount(List<T> list) {
        return list.stream().distinct().count();
    }
}
