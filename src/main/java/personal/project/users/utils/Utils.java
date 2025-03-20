package personal.project.users.utils;

import personal.project.users.domain.dto.UserDTO;
import personal.project.users.exception.InvalidParameterException;

import java.util.Map;

public class Utils {
    public static void validateUser(UserDTO user) {
        if (user == null) {
            throw new InvalidParameterException("UserDTO");
        }

        Map<String, String> fields = Map.of(
                "Username", user.getUsername(),
                "Password", user.getPassword(),
                "Email", user.getEmail(),
                "Birth", user.getBirth(),
                "Phone", user.getPhone()
        );

        fields.entrySet().stream()
                .filter(entry -> entry.getValue() == null || entry.getValue().isBlank())
                .findFirst()
                .ifPresent(entry -> {
                    throw new InvalidParameterException(entry.getKey());
                });

        if (!user.getEmail().contains("@")) {
            throw new InvalidParameterException("Email");
        }
    }
}
