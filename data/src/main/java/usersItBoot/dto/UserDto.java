package usersItBoot.dto;

import lombok.Data;
@Data
public class UserDto {
    private String surname;
    private String name;
    private String patronymic;
    private String email;
    private String role;
}
