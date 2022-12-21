package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;


@Component
public class EmailValidator implements Validator {
    @Autowired
    ApplicationContext context;
    @Autowired
    UserRepository repository;

    public EmailValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserTo.class);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        UserTo user = (UserTo) obj;
        String email = user.getEmail().toLowerCase();
        User currUser = repository.getByEmail(email);
        if (currUser != null) {
            if (!currUser.getId().equals(user.getId())) {
//                if (!currUser.getId().equals(SecurityUtil.authUserId()))
                    errors.rejectValue("email", "email.already.exists", "This email already exists");
            }
        }
    }
}
