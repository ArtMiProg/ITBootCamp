package userItBoot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import usersItBoot.UserController;
import usersItBoot.UserService;
import usersItBoot.dto.UserDto;
import usersItBoot.entity.Role;
import usersItBoot.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setup() {
        mockMvc = standaloneSetup(userController).build();
    }

    @Test
    public void testAddUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setSurname("Smith");
        userDto.setName("John");
        userDto.setEmail("john.smith@example.com");

        User user = new User();
        user.setId(1);
        user.setSurname("Smith");
        user.setName("John");
        user.setEmail("john.smith@example.com");

        when(userService.addUser(any(UserDto.class))).thenReturn(user);
        when(userService.saveUser(any(User.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"surname\":\"Smith\",\"name\":\"John\",\"email\":\"john.smith@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User added successfully"));
    }

    @Test
    public void testAddUser_InvalidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\",\"email\":\"john.smith@example.com\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAll() throws Exception {
        List<UserDto> userDtoList = new ArrayList<>();
        UserDto userDtoOne = new UserDto();
        userDtoOne.setSurname("Parker");
        userDtoOne.setName("Jonatan");
        userDtoOne.setPatronymic("Reez");
        userDtoOne.setEmail("pjr@gmail.com");
        userDtoOne.setRoles(Arrays.asList(Role.ROLE_USER));

        UserDto userDtoTwo = new UserDto();
        userDtoTwo.setSurname("Forester");
        userDtoTwo.setName("Antuan");
        userDtoTwo.setPatronymic("Ray");
        userDtoTwo.setEmail("far@gmail.com");
        userDtoTwo.setRoles(Arrays.asList(Role.ROLE_USER));

        userDtoList.add(userDtoOne);
        userDtoList.add(userDtoTwo);

        when(userService.getAll(anyInt(), anyInt())).thenReturn(userDtoList);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json("["
                        + "{"
                        + "\"surname\":\"Parker\","
                        + "\"name\":\"Jonatan\","
                        + "\"patronymic\":\"Reez\","
                        + "\"email\":\"pjr@gmail.com\","
                        + "\"roles\":[\"ROLE_USER\"]"
                        + "},"
                        + "{"
                        + "\"surname\":\"Forester\","
                        + "\"name\":\"Antuan\","
                        + "\"patronymic\":\"Ray\","
                        + "\"email\":\"far@gmail.com\","
                        + "\"roles\":[\"ROLE_USER\"]"
                        + "}"
                        + "]"
                ));

        verify(userService, times(1)).getAll(anyInt(), anyInt());
    }

}
