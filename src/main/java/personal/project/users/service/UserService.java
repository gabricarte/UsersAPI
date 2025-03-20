package personal.project.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import personal.project.users.domain.dto.UserDTO;
import personal.project.users.domain.entity.User;
import personal.project.users.repository.UserRepository;
import personal.project.users.utils.Utils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(String username){
        return userRepository.findUserByUsername(username);
    }

    public User registerUser(UserDTO user){
        Utils.validateUser(user);
        return userRepository.save(user.toEntity());
    }
}
