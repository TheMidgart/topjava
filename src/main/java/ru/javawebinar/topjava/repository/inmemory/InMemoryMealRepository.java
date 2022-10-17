package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        } else {

            return Objects.equals(meal.getUserId(), repository.get(meal.getId()).getUserId()) ?
                    repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return userId == repository.get(id).getUserId() && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return userId == repository.get(id).getUserId() ? repository.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values()
                .stream()
                .sorted(((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime())))
                .collect(Collectors.toList());
    }

    public Collection<Meal> getAllWithUserId(Integer userId) {
        return getAll()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .collect(Collectors.toList());
    }

    public Collection<Meal> getFilteredByDate(LocalDate fromDate, LocalDate toDate, Integer userId) {
        return getAllWithUserId(userId).stream()
                .filter(meal -> meal.getDateTime().toLocalDate().toEpochDay() >= fromDate.toEpochDay())
                .filter(meal -> meal.getDateTime().toLocalDate().isBefore(toDate))
                .collect(Collectors.toList());

    }
}

