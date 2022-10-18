package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.filters.MealFilterParams;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

public class MealServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    @Autowired
    private MealRestController controller;

    @Override
    public void init() {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            controller = appCtx.getBean(MealRestController.class);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("in controller post");
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")), 0);
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        if (meal.isNew()) controller.create(meal);
        else controller.update(meal);
        response.sendRedirect("meals");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("in controller get");
        String action = request.getParameter("action");
        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete id={}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        controller.toTemplateForCreate() :
                        controller.get(getId(request));
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
                    request.setAttribute("meals", controller.getAllWithParams(params));
                    request.setAttribute("filterParams", params);
                } else {
                    log.info("get All for current userId = " + SecurityUtil.authUserId());
                    request.setAttribute("meals", controller.getAll());
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



