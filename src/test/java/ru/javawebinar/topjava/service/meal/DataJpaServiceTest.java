package ru.javawebinar.topjava.service.meal;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles(profiles = {Profiles.DATAJPA})
public class DataJpaServiceTest extends MealServiceTest {

    @Override
    public void getMealWithUser() {
        Meal meal = service.getMealsWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(meal, adminMeal1);
        USER_MATCHER.assertMatch(meal.getUser(), UserTestData.admin);

    }

}
