package usersItBoot.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import usersItBoot.UserService;
import usersItBoot.dto.UserDto;
import usersItBoot.entity.Role;
import usersItBoot.entity.User;
import usersItBoot.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public User addUser(UserDto userDto) {
        User user = new User();
        user.setSurname(userDto.getSurname());
        user.setName(userDto.getName());
        user.setPatronymic(userDto.getPatronymic());
        user.setEmail(userDto.getEmail());
        user.setRoles(Arrays.asList(Role.ROLE_USER));
        LOGGER.info("Service method addUser executed");
        LOGGER.error("Service method addUser failed");
        return user;
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setSurname(user.getSurname());
            userDto.setName(user.getName());
            userDto.setPatronymic(user.getPatronymic());
            userDto.setEmail(userDto.getEmail());
            userDto.setRoles(Arrays.asList(Role.ROLE_USER));
            usersDto.add(userDto);
        }
        LOGGER.info("Service method getAll executed");
        LOGGER.error("Service method getAll failed");
        return usersDto;
    }

    @Override
    public boolean saveUser(User user) {
        try {
            userRepository.save(user);
            LOGGER.info("Service method saveUser executed");
            return true;
        } catch (Exception e) {
            LOGGER.error("Service method saveUser failed");
            return false;
        }
    }
}
