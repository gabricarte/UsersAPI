package personal.project.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.project.users.domain.dto.UserDTO;
import personal.project.users.domain.entity.User;
import personal.project.users.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username){
        return ResponseEntity.ok(userService.getUser(username));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO){
        User newUser = userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
}
