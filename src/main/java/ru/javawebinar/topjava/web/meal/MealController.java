package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.filters.MealFilterParams;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.MealServlet;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Controller
public class MealController {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private final MealService service;

    @Autowired
    public MealController(MealService service) {
        this.service = service;
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("in controller post");
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")), userId);
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        service.update(meal);
        response.sendRedirect("meals");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("in controller get");
        String action = request.getParameter("action");
        int userId = SecurityUtil.authUserId();

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete id={}", id);
                service.delete(id, userId);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000,
                                userId) :
                        service.get(getId(request), userId);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                MealFilterParams params = new MealFilterParams(request.getParameter("fromDate"),
                        request.getParameter("toDate"), request.getParameter("fromTime"),
                        request.getParameter("toTime"));
                if (params.hasParams()) {
                    log.info("get filtered for current userId = " + SecurityUtil.authUserId());
                    request.setAttribute("meals", MealsUtil.filterByPredicates(service.getAllWithUserId(SecurityUtil.authUserId()),
                            SecurityUtil.authUserCaloriesPerDay(), params.toPredicateList()));
                    request.setAttribute("filterParams", params);
                } else {
                    log.info("get All for current userId = " + SecurityUtil.authUserId());
                    request.setAttribute("meals", MealsUtil.getTos(service.getAllWithUserId(SecurityUtil.authUserId()),
                            SecurityUtil.authUserCaloriesPerDay()));
                }
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

}
