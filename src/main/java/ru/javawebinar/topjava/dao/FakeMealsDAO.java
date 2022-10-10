package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.exception.MealsDBException;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repo.MealsRepo;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FakeMealsDAO implements MealsRepo<Meal, Integer> {
    private final Map<Integer, Meal> mealsDAO;
    public static final AtomicInteger idCounter = new AtomicInteger(0);

    public FakeMealsDAO() {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        meals.forEach(a -> a.setId(idCounter.getAndIncrement()));
        mealsDAO = meals.stream().collect(Collectors.toConcurrentMap(Meal::getId, Function.identity()));
    }

    @Override
    public Meal getById(Integer id) throws MealsDBException {
        if (!mealsDAO.containsKey(id)) throw new MealsDBException();
        return mealsDAO.get(id);
    }

    @Override
    public void deleteById(Integer id) throws MealsDBException {
        if (!mealsDAO.containsKey(id)) throw new MealsDBException();
        mealsDAO.remove(id);
    }

    @Override
    public List<Meal> getAll() {

        return new ArrayList<>(mealsDAO.values());
    }

    @Override
    public void update(Meal meal) throws MealsDBException {
        if (!mealsDAO.containsKey(meal.getId())) throw new MealsDBException();
        mealsDAO.put(meal.getId(), meal);
    }

    @Override
    public void add(Meal meal) {
        meal.setId(idCounter.getAndIncrement());
        mealsDAO.put(meal.getId(), meal);
    }
}
