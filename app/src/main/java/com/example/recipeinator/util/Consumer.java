package com.example.recipeinator.util;

@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}
