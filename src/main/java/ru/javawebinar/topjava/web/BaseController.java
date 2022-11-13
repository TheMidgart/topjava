package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public abstract class BaseController {
    protected static final Logger log = LoggerFactory.getLogger(BaseController.class);

}
