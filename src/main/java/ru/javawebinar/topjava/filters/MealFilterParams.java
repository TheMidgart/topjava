package ru.javawebinar.topjava.filters;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class MealFilterParams {
    LocalDate fromDate;
    LocalDate toDate;
    LocalTime fromTime;
    LocalTime toTime;

    public MealFilterParams(String fromDate, String toDate, String fromTime, String toTime) {
        this.fromDate = (fromDate != null && !fromDate.equals("")) ? LocalDate.parse(fromDate) : null;
        this.toDate = (toDate != null && !toDate.equals("")) ? LocalDate.parse(toDate) : null;
        this.fromTime = (fromTime != null && !fromTime.equals("")) ? LocalTime.parse(fromTime) : null;
        this.toTime = (toTime != null && !toTime.equals("")) ? LocalTime.parse(toTime) : null;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public LocalTime getFromTime() {
        return fromTime;
    }

    public LocalTime getToTime() {
        return toTime;
    }

    public boolean hasParams() {
        return fromDate != null || fromTime != null || toDate != null || toTime != null;
    }

    public List<Predicate<Meal>> toDatePredicates() {
        List<Predicate<Meal>> predicates = new ArrayList<>();
        if (fromDate != null) predicates.add(meal -> meal.getDate().toEpochDay() >= getFromDate().toEpochDay());
        if (toDate != null) predicates.add(meal -> meal.getDate().toEpochDay() < getToDate().toEpochDay());
        return predicates;
    }

    public List<Predicate<Meal>> toTimePredicates() {
        List<Predicate<Meal>> predicates = new ArrayList<>();
        if (fromTime != null) predicates.add(meal -> meal.getDateTime().toLocalTime().toNanoOfDay()
                >= getFromTime().toNanoOfDay());
        if (toTime != null) predicates.add(meal -> meal.getDateTime().toLocalTime().toNanoOfDay()
                < getToTime().toNanoOfDay());
        return predicates;

    }
}
