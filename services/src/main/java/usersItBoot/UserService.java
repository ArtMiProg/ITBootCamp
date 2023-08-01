package usersItBoot;

import usersItBoot.dto.UserDto;
import usersItBoot.dto.UserShortDto;
import usersItBoot.entity.User;

import java.util.List;

public interface UserService {

    User addUser (UserDto userDto);

    List<UserShortDto> getAll(int pageNum, int pageSize);

    boolean saveUser(User user);
}
