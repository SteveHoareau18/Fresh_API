package fr.steve.fresh_api.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ListUtils {
    public static <T> List<T> iteratorToList(Iterable<T> iterable) {
        return StreamSupport
                .stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
