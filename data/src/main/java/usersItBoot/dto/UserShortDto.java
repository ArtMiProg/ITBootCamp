package usersItBoot.dto;

import lombok.Data;
import usersItBoot.entity.Role;

import java.util.List;

@Data
public class UserShortDto {
    private String fullName;
    private String email;
    private List<Role> roles;
}
