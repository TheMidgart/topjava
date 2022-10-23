package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;



@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, USER_ID);
        MealTestData.assertMatch(meal,MealTestData.meal);
    }

    @Test
    public void getWithStranger() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID,STRANGER_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND,USER_ID));
    }

    @Test
    public void delete() {service.delete(MEAL_ID,USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID,USER_ID));
    }

    @Test
    public void deleteWithStranger(){
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID,STRANGER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND,USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        MealTestData.assertMatch(service.getBetweenInclusive(START_DATE.toLocalDate(),END_DATE.toLocalDate(),USER_ID),
                FILTERED_USER_MEALS);
    }

    @Test
    public void getAll() {
        MealTestData.assertMatch(service.getAll(USER_ID),USER_MEALS);
    }

    @Test
    public void getAllForAnotherUser() {
        MealTestData.assertMatch(service.getAll(ADMIN_ID),ADMIN_MEALS);
    }


    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(),USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId,USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate(){
        Meal anotherMeal = new Meal(meal);
        anotherMeal.setDescription("another meal");
        anotherMeal.setId(null);
        assertThrows(DataAccessException.class,()-> service.create(anotherMeal,USER_ID));
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, USER_ID);
        MealTestData.assertMatch(service.get(MEAL_ID, USER_ID), MealTestData.getUpdated());
    }
    @Test
    public void updateWithStanger(){
        Meal strangerMeal = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(strangerMeal,STRANGER_ID));
    }
}