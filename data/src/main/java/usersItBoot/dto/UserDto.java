package usersItBoot.dto;

import lombok.Data;
import usersItBoot.entity.Role;

import java.util.List;

@Data
public class UserDto {
    private String surname;
    private String name;
    private String patronymic;
    private String email;
    private List<Role> roles;
}
