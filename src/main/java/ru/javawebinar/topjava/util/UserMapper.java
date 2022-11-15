package ru.javawebinar.topjava.util;

import org.springframework.jdbc.core.ResultSetExtractor;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.*;

public class UserMapper {
    public static final ResultSetExtractor<List<User>> userWithRolesResultExtractor = (resultSet) -> {
        Map<Integer, User> mapUser = new HashMap<>();
        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            if (!mapUser.containsKey(id)) {
                User user = new User();
                user.setId(id);
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setEnabled(resultSet.getBoolean("enabled"));
                user.setCaloriesPerDay(resultSet.getInt("calories_per_day"));
                user.setRoles(new HashSet<>());
                mapUser.put(id,user);
                }
            if (resultSet.getString("role") != null) {
                mapUser.get(id).getRoles().add(Role.valueOf((resultSet.getString("role"))));
            }
        }
        return mapUser.values().stream().toList();
    };
}

