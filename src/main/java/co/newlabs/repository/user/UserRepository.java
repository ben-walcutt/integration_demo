package co.newlabs.repository.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private List<UserEntity> users;
    private Map<String, String> roles;

    public UserRepository() {
        users = new ArrayList<>();
        roles = new HashMap<>();

        roles.put("user@user.com", "ROLE_ADMIN");
    }

    public UserEntity getUserByEmail(String email) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findAny().orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void saveUser(UserEntity user) {
        user.setUserId(users.size());
        users.add(user);
        roles.put(user.getEmail(), "ROLE_ADMIN");
    }

    public String getRole(String email) {
        return roles.get(email);
    }
}
