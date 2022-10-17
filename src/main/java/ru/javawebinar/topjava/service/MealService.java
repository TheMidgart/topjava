package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {


    private final MealRepository repository;
    @Autowired
    public MealService (MealRepository repository ){
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection <Meal> getFilteredByDate(LocalDate fromDate, LocalDate toDate, Integer userId){
        return checkNotFound(getAllWithUserId(userId).stream()
                .filter(meal -> meal.getDateTime().toLocalDate().toEpochDay()>=fromDate.toEpochDay())
                .filter(meal -> meal.getDateTime().toLocalDate().isBefore(toDate))
                .collect(Collectors.toList()),"meals not found");
    }

    public Collection<Meal> getAllWithUserId(Integer userId){
        return getAll()
                .stream()
                .filter(meal -> meal.getUserId()==userId)
                .collect(Collectors.toList());
    }
    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    public void update(Meal meal) {
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }

}