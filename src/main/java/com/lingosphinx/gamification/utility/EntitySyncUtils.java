// src/main/java/com/lingosphinx/lesson/util/EntitySyncUtils.java
package com.lingosphinx.gamification.utility;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntitySyncUtils {

    public static <E, D, K> void syncChildEntities(
            List<E> existingEntities,
            List<D> dtoEntities,
            Function<E, K> entityIdExtractor,
            Function<D, K> dtoIdExtractor,
            Function<D, E> dtoToEntityMapper,
            Consumer<E> onAdd,
            BiConsumer<D, E> onUpdate
    ) {
        var dtoIds = dtoEntities.stream()
                .map(dtoIdExtractor)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        var existingIds = existingEntities.stream()
                .map(entityIdExtractor)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        var toRemove = existingEntities.stream()
                .filter(e -> !dtoIds.contains(entityIdExtractor.apply(e)))
                .toList();
        existingEntities.removeAll(toRemove);

        var toAdd = dtoEntities.stream()
                .filter(d -> dtoIdExtractor.apply(d) == null || !existingIds.contains(dtoIdExtractor.apply(d)))
                .map(dtoToEntityMapper)
                .toList();
        toAdd.forEach(onAdd);
        existingEntities.addAll(toAdd);

        dtoEntities.stream()
                .filter(d -> {
                    var id = dtoIdExtractor.apply(d);
                    return id != null && existingIds.contains(id);
                })
                .forEach(d -> {
                    var id = dtoIdExtractor.apply(d);
                    var entity = existingEntities.stream()
                            .filter(e -> Objects.equals(entityIdExtractor.apply(e), id))
                            .findFirst()
                            .orElse(null);
                    if (entity != null) {
                        onUpdate.accept(d, entity);
                    }
                });
    }
}