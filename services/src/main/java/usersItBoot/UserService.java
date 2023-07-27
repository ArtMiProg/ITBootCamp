package usersItBoot;

import usersItBoot.dto.UserDto;
import usersItBoot.entity.User;

import java.util.List;

public interface UserService {

    User addUser (UserDto userDto);

    List<UserDto> getAll();

    boolean saveUser(User user);
}
