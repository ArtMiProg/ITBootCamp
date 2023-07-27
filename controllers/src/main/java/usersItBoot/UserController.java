package usersItBoot;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import usersItBoot.dto.UserDto;
import usersItBoot.entity.User;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(value = "/addUser")
    public UserDto showRegistrationForm() {
        return new UserDto();
    }

    @PostMapping(value = "/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        User user = userService.addUser(userDto);
        if (userService.saveUser(user)) {
            return ResponseEntity.ok("User added successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to add user");
        }
    }

    @GetMapping(value = "/users")
    public List<User> getAll() {
        return userService.getAll();
    }

}
