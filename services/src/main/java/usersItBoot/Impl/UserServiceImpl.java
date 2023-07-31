package usersItBoot.Impl;

import exeptions.InvalidUserInputException;
import exeptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import usersItBoot.UserService;
import usersItBoot.dto.UserDto;
import usersItBoot.entity.Role;
import usersItBoot.entity.User;
import usersItBoot.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private User convertToUser(UserDto userDto) {
        User user = new User();
        user.setSurname(userDto.getSurname());
        user.setName(userDto.getName());
        user.setPatronymic(userDto.getPatronymic());
        user.setEmail(userDto.getEmail());
        user.setRoles(Arrays.asList(Role.ROLE_USER));
        return user;
    }
    @Override
    public User addUser(UserDto userDto) {
        try {
            if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
                throw new InvalidUserInputException("Email cannot be empty");
            }
            User user = convertToUser(userDto);
            LOGGER.info("Service method addUser executed");
            LOGGER.error("Service method addUser failed");
            return user;
        } catch (InvalidUserInputException e) {
            LOGGER.error("Invalid user input: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error while adding user: " + e.getMessage());
            throw new RuntimeException("Failed to add user", e);
        }
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setSurname(user.getSurname());
        userDto.setName(user.getName());
        userDto.setPatronymic(user.getPatronymic());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(Arrays.asList(Role.ROLE_USER));
        return userDto;
    }
    @Override
    public List<UserDto> getAll(int pageNum, int pageSize) {
        try {
            List<User> users = userRepository.findAll();
            if (pageNum < 1){
                pageNum = 1;
            }
            pageNum = pageNum - 1;
            if(pageNum > (users.size()/10 - 1)){
                pageNum = users.size()/10;
            }
            //http://localhost:8080/users?pageNum=1 - shows page No 1, ?pageNum=2 - page 2 etc.
            Pageable pageable = PageRequest.of(pageNum, 10);
            Page<User> usersPage = userRepository.findAll(pageable);
            List<UserDto> usersDto = new ArrayList<>();
            for (User user : usersPage.getContent()) {
                UserDto userDto = convertToDto(user);
                usersDto.add(userDto);
            }
            Collections.sort(usersDto, (userDtoOne, userDtoTwo) -> {
                return userDtoOne.getEmail().compareTo(userDtoTwo.getEmail());
            });
            LOGGER.info("Service method getAll executed");
            LOGGER.error("Service method getAll failed");
            if (users.isEmpty()) {
                throw new UserNotFoundException("No users found");
            }
            return usersDto;
        } catch (Exception e) {
            LOGGER.error("Error while retrieving users: " + e.getMessage());
            throw new RuntimeException("Failed to retrieve users", e);
        }
    }

    @Override
    public boolean saveUser(User user) {
        try {
            userRepository.save(user);
            LOGGER.info("Service method saveUser executed");
            return true;
        } catch (Exception e) {
            LOGGER.error("Service method saveUser failed");
            throw new RuntimeException("Failed to save user", e);
        }
    }
}
