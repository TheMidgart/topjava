package ru.javawebinar.topjava.repo;

import java.util.List;

public interface MealsRepo<T, V> {
    T getById(V id);

    void deleteById(V id);

    List<T> getAll();

    void add(T meal);

    void update(T meal);
}
