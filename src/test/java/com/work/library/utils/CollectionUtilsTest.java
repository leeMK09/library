package com.work.library.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollectionUtilsTest {
    @Test
    void 두_리스트의_길이가_다르다면_결과는_거짓이다() {
        List<Integer> list1 = List.of(1, 2, 3);
        List<Integer> list2 = List.of(1, 2);

        boolean result = CollectionUtils.isEqualsSize(list1, list2);

        assertFalse(result);
    }

    @Test
    void 두_리스트의_길이가_같다면_결과는_참이다() {
        List<Integer> list1 = List.of(1, 2, 3);
        List<Integer> list2 = List.of(1, 2, 3);

        boolean result = CollectionUtils.isEqualsSize(list1, list2);

        assertTrue(result);
    }
}
