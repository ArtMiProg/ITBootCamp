package usersItBoot;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import usersItBoot.dto.UserDto;
import usersItBoot.entity.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping(value = "/addUser")
    public UserDto showRegistrationForm() {
        return new UserDto();
    }

    @PostMapping(value = "/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        User user = userService.addUser(userDto);
        if (userService.saveUser(user)) {
            LOGGER.info("User added successfully");
            return ResponseEntity.ok("User added successfully");
        } else {
            LOGGER.error("Failed to add user");
            return ResponseEntity.badRequest().body("Failed to add user");
        }
    }

    @GetMapping(value = "/users")
    public List<UserDto> getAll() {
        LOGGER.info("List of users provided");
        LOGGER.error("Failed to show list of users");
        return userService.getAll();
    }
}
