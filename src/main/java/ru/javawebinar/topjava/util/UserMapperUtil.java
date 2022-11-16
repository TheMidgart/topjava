package ru.javawebinar.topjava.util;

import org.springframework.jdbc.core.ResultSetExtractor;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.*;

public class UserMapperUtil {
    public static final ResultSetExtractor<List<User>> userWithRolesResultExtractor = (resultSet) -> {
        Map<Integer, User> mapUser = new LinkedHashMap<>();
        List<User> users = new ArrayList<>();
        User tempUser = new User();
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

    public static Map<String,Object>[] toUserRolesBatches(User user){
        List<Role> list = new ArrayList<>(user.getRoles());
        Map<String,Object>[] batches = new HashMap[list.size()];
        for (int i = 0; i < list.size() ; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("role",String.valueOf(list.get(i)));
            map.put("user_id",user.getId());
            batches[i]=map;
        }
        return batches;
    }
}

