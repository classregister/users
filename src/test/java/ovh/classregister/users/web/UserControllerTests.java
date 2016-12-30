package ovh.classregister.users.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ovh.classregister.users.domain.User;
import ovh.classregister.users.domain.UserBody;
import ovh.classregister.users.service.UserService;

import java.util.List;

import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@EnableSpringDataWebSupport
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldGetUsers() throws Exception {
        PageRequest pageable = new PageRequest(0, 4);
        Page<User> pageWithUsers = createPageWithUsers(pageable);
        String expectedJson = createJson(pageWithUsers);
        given(userService.getUsers(pageable)).willReturn(pageWithUsers);

        mockMvc.perform(get("/users?number=1&size=4").accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldGetUser() throws Exception {
        long userId = 1L;
        User expectedUser = createUser(userId, "user", "password");
        String expectedJson = createJson(expectedUser);
        given(userService.getUser(userId)).willReturn(expectedUser);


        mockMvc.perform(get("/users/" + userId).accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(content().json(expectedJson));
    }

    @Test
    public void shouldAddUser() throws Exception {
        long userId = 0;
        UserBody userBody = new UserBody("someUser", "password");
        String bodyJson = createJson(userBody);
        User expectedUser = createUser(userId,"someUser", "password");
        given(userService.addUser(userBody)).willReturn(expectedUser);


        mockMvc.perform(post("/users").content(bodyJson)
                                      .contentType(MediaType.APPLICATION_JSON_VALUE)
                                      .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        long userId = 1;
        UserBody userBody = new UserBody("someUser", "password");
        String bodyJson = createJson(userBody);
        User expectedUser = createUser(userId, "someUser", "password");
        given(userService.editUser(userId, userBody)).willReturn(expectedUser);


        mockMvc.perform(put("/users/" + userId).content(bodyJson)
                                               .contentType(MediaType.APPLICATION_JSON_VALUE)
                                               .accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        long userId = 1;

        mockMvc.perform(delete("/users/" + userId))
               .andExpect(status().isNoContent());
    }

    private Page<User> createPageWithUsers(final Pageable pageable) {
        List<User> content = createUsers();
        return new PageImpl<>(content, pageable, content.size());
    }

    private List<User> createUsers() {
        User user = createUser(1, "user", "password");
        User user2 = createUser(2, "user2", "password");
        User user3 = createUser(3, "user3", "password");
        User user4 = createUser(4, "user4", "password");
        return newArrayList(user, user2, user3, user4);
    }

    private User createUser(long id, String login, String password) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);

        return user;
    }

    private String createJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
