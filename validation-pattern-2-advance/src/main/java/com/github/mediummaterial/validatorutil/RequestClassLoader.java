package com.github.mediummaterial.validatorutil;


import com.github.mediummaterial.util.StringUtils;
import lombok.Builder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Builder(builderClassName = "ClassBuilder")
public class RequestClassLoader {

    private final ConcurrentHashMap<Class<?>, List<String>> requests;


    public List<String> findByClass(Class<?> clazz) {
        return requests.getOrDefault(clazz, new ArrayList<>());
    }

    public static ClassBuilder builder() {
        return new ClassBuilder()
                .requests(new ConcurrentHashMap<>());
    }

    public static class ClassBuilder {
        public ClassBuilder addRequest(Iterable<Class<?>> allRequests) {
            List<CompletableFuture<Void>> futures = new ArrayList<>();
            for (Class<?> request : allRequests) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    this.addRequest(request);
                });
                futures.add(future);
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            }
            return this;
        }

        private void addRequest(Class<?> request) {
            Field[] declaredFields = request.getDeclaredFields();
            List<String> columnMappings = new ArrayList<>();
            for (Field field : declaredFields) {
                DuplicationInDB annotation = field.getAnnotation(DuplicationInDB.class);
                if (annotation != null) {
                    String colDb = field.getName();
                    if (!annotation.columnName().equals("NOT_DEFINED")) {
                        StringUtils.toSnakeCase(colDb);
                        colDb = annotation.columnName();
                    }

                    String mapping = colDb;
                    columnMappings.add(mapping);
                }
            }

            this.requests.put(request, columnMappings);
        }
    }
}
