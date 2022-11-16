package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import javax.validation.ValidationException;

import static ru.javawebinar.topjava.MealTestData.incorrectMeal;
import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void checkIncorrect(){
        Assert.assertThrows(ValidationException.class,()->service.create(incorrectMeal,USER_ID));
    }


}