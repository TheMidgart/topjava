package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 3;
    public static final int ADMIN_MEAL_ID = MEAL_ID + 7;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int STRANGER_ID = ADMIN_ID;

    public static final int NOT_FOUND = 500;
    public static final LocalDateTime START_DATE = LocalDateTime.of(2022, 10, 21, 10, 0);
    public static final LocalDateTime END_DATE = LocalDateTime.of(2022, 10, 21, 22, 0);


    public static final Meal meal = new Meal(MEAL_ID, LocalDateTime.of(2022, 10, 21, 10, 0),
            "Завтрак", 500);

    public static final List<Meal> USER_MEALS = Stream.of(
                    new Meal(MEAL_ID, LocalDateTime.of(2022, 10, 21, 10, 0),
                            "Завтрак", 500),
                    new Meal(MEAL_ID + 1, LocalDateTime.of(2022, 10, 21, 13, 0),
                            "Обед", 1000),
                    new Meal(MEAL_ID + 2, LocalDateTime.of(2022, 10, 21, 20, 0),
                            "Ужин", 500),
                    new Meal(MEAL_ID + 3, LocalDateTime.of(2022, 10, 22, 0, 0),
                            "Еда на граничное значение", 500),
                    new Meal(MEAL_ID + 4, LocalDateTime.of(2022, 10, 22, 10, 0),
                            "Завтрак", 500),
                    new Meal(MEAL_ID + 5, LocalDateTime.of(2022, 10, 22, 13, 0),
                            "Обед", 1000),
                    new Meal(MEAL_ID + 6, LocalDateTime.of(2022, 10, 22, 20, 0),
                            "Ужин", 400)
            )
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static final List<Meal> ADMIN_MEALS = Stream.of(
                    new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2022, 10, 21, 10, 0),
                            "ADMIN Завтрак", 500),
                    new Meal(ADMIN_MEAL_ID + 1, LocalDateTime.of(2022, 10, 21, 13, 0),
                            "ADMIN Обед", 1000),
                    new Meal(ADMIN_MEAL_ID + 2, LocalDateTime.of(2022, 10, 21, 20, 0),
                            "ADMIN Ужин", 500)

            )
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());

    public static final List<Meal> FILTERED_USER_MEALS = Stream.of(
                    new Meal(MEAL_ID, LocalDateTime.of(2022, 10, 21, 10, 0),
                            "Завтрак", 500),
                    new Meal(MEAL_ID + 1, LocalDateTime.of(2022, 10, 21, 13, 0),
                            "Обед", 1000),
                    new Meal(MEAL_ID + 2, LocalDateTime.of(2022, 10, 21, 20, 0),
                            "Ужин", 500))
            .sorted(Comparator.comparing(Meal::getDateTime).reversed())
            .collect(Collectors.toList());


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal);
        updated.setDateTime(LocalDateTime.of(2030, 6, 6, 6, 0));
        updated.setDescription("UpdatedDescription");
        updated.setCalories(666);
        return updated;
    }

    public static Meal getNew(){
        return new Meal(null,LocalDateTime.of(2030, 6, 6, 6, 0),
                "NEW",666);

    }
}

