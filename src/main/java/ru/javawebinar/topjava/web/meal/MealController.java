package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.filters.MealFilterParams;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.MealServlet;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

@Controller
public class MealController {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private final MealService service;

    @Autowired
    public MealController(MealService service) {
        this.service = service;
    }

    public Collection<MealTo> getAll() {
        return MealsUtil.getTos(service.getAllWithUserId(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public Collection<MealTo> getAllWithParams(MealFilterParams params) {
        return MealsUtil.filterByPredicates(service.getFilteredByDate(params.toDatePredicates(),
                SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay(), params.toTimePredicates());
    }

    public void delete(int id) {
        log.debug("Controller delet meal with id " + id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal) {
        meal.setUserId(SecurityUtil.authUserId());
        log.info("update meal " + meal);
        service.update(meal);
    }

    public void create(Meal meal) {
        meal.setUserId(SecurityUtil.authUserId());
        log.info("create meal " + meal);
        service.create(meal);
    }

    public Meal get(int id) {
        log.debug("Controller get meal with id " + id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal toTemplateForCreate() {
        return new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000,
                SecurityUtil.authUserId());

    }

}
