package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends BaseController {
    @Autowired
    MealService service;


    @GetMapping
    public String getAll(Model model) {
        log.info("meals");
        model.addAttribute("meals", MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping(value = "", params = "action=filter")
    public String getFiltered(HttpServletRequest request) {
        log.info("filter");
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        request.setAttribute("meals",
                MealsUtil.getFilteredTos(service.getBetweenInclusive(startDate, endDate, SecurityUtil.authUserId()),
                        SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }


    @GetMapping(value = "", params = "action=update")
    public String updateReq(@RequestParam("id") int id, Model model) {
        log.info("update");
        Meal meal = service.get(id, SecurityUtil.authUserId());
        log.debug(meal.toString());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping(value = "", params = "action=create")
    public String createReq(Model model) {
        log.info("create");
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping(value = "", params = "action=delete")
    public String deleteReq(@RequestParam("id") int id) {
        log.info("delete");
        service.delete(id, SecurityUtil.authUserId());
        return "meals";
    }

    @PostMapping
    public String save(HttpServletRequest request) {
        log.info("post:save");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.hasLength(request.getParameter("id"))) {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            service.update(meal, SecurityUtil.authUserId());
        } else {
            service.create(meal, SecurityUtil.authUserId());
        }
        log.info(meal.toString()+" userID= "+SecurityUtil.authUserId());
        return "redirect:meals";
    }
}
