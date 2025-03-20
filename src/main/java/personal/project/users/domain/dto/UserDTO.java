package personal.project.users.domain.dto;

import lombok.Builder;
import lombok.Data;
import personal.project.users.domain.entity.User;

import java.util.Date;
@Data
@Builder
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String birth;
    private String phone;
    public User toEntity(){
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .birth(birth)
                .phone(phone)
                .build();
    }
}
