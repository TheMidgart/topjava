package ru.javawebinar.topjava.service.user;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.MealTestData.meals;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Override
    public void getUserWithMeal() {
        User user = service.getUserWithMeals(USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.user);
        MEAL_MATCHER.assertMatch(user.getMeals(), meals);

    }
}
