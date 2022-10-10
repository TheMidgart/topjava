package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.dao.FakeMealsDAO;
import ru.javawebinar.topjava.repo.MealsRepo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    public static final int CALORIES_PER_DAY = 2000;
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_MEALS = "/meals.jsp";
    private final MealsRepo<Meal, Integer> dao = new FakeMealsDAO();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final Logger log = getLogger(MealServlet.class);


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in get Request");
        String forward;
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            log.debug("delete Meal");
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            log.debug("meal for delete" + dao.getById(mealId).toString());
            dao.deleteById(mealId);
            forward = LIST_MEALS;
            request.setAttribute("meals", MealsUtil.filteredByStreams(dao.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), CALORIES_PER_DAY));
        } else if (action.equalsIgnoreCase("edit")) {
            log.debug("edit Meal");
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = dao.getById(mealId);
            log.debug("editing meal is" + meal.toString());
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMeals")) {
            log.debug("show all meals");
            forward = LIST_MEALS;
            request.setAttribute("meals", MealsUtil.filteredByStreams(dao.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), CALORIES_PER_DAY));
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("in Post");
        Meal meal;
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("mealDateTime"), formatter);
        meal = new Meal(dateTime, request.getParameter("mealDescription"), Integer.parseInt(request.getParameter("mealCalories")));
        if ((request.getParameter("mealId") != null) && (!request.getParameter("mealId").equals(""))) {
            log.debug("updating meal");
            meal.setId(Integer.parseInt(request.getParameter("mealId")));
            log.debug(meal.toString());
            dao.update(meal);
        } else {
            String str = request.getParameter("mealDescription");
            log.debug("creating new meal" + meal);
            dao.add(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEALS);
        request.setAttribute("meals", MealsUtil.filteredByStreams(dao.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), CALORIES_PER_DAY));
        view.forward(request, response);
        log.debug("show all meals");
    }


}
