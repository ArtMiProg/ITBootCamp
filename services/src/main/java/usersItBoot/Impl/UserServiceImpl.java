package usersItBoot.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import usersItBoot.UserService;
import usersItBoot.dto.UserDto;
import usersItBoot.entity.Role;
import usersItBoot.entity.User;
import usersItBoot.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User addUser(UserDto userDto) {
        User user = new User();
        user.setSurname(userDto.getSurname());
        user.setName(userDto.getName());
        user.setPatronymic(userDto.getPatronymic());
        user.setEmail(userDto.getEmail());
        user.setRoles(Arrays.asList(Role.ROLE_USER));
        return user;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean saveUser(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
