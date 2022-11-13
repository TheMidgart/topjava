package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends BaseController {
    @Autowired
    MealService service;


    @GetMapping("/")
    public String getMeals(Model model) {
        log.info("meals");
        model.addAttribute("meals", MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

  /*  @DeleteMapping("/{id}")
    public String deleteMeal(@RequestParam int id){
        log.info("delete meal with id ="+id);
        service.delete(id,SecurityUtil.authUserId());
        return "meals";
    }*/
}
