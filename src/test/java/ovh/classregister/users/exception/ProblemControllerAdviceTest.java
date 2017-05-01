package ovh.classregister.users.exception;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ovh.classregister.users.domain.UserBody;
import ovh.classregister.users.web.UserController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class ProblemControllerAdviceTest {

    private MockMvc mockMvc;

    @Mock
    private UserController userController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                                      .setControllerAdvice(new ProblemControllerAdvice())
                                      .build();
    }

    @Test
    public void shouldReturnProblemJsonWhenResourceNotFoundExceptionIsThrown() throws Exception {
        long userId = 1;
        when(userController.getUser(userId)).thenThrow(new ResourceNotFoundException(userId));

        mockMvc.perform(get("/users/{id}", userId))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.type").value("/users/" + userId))
               .andExpect(jsonPath("$.title").value("User does not exist"))
               .andExpect(jsonPath("$.detail").value(String.format("User with id: %d not found", userId)))
               .andExpect(jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void shouldReturnProblemJsonWhenDataIntegrityExceptionIsThrown() throws Exception {
        UserBody userBody = new UserBody("user_login", "user_password");
        String body = createJson(userBody);

        doThrow(new DataIntegrityViolationException("Invalid login")).when(userController)
                                                                     .createUser(anyObject());

        mockMvc.perform(post("/users").content(body)
                                      .contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.title").value("Invalid login"))
               .andExpect(jsonPath("$.detail").value("Login already exists. Login must be unique"))
               .andExpect(jsonPath("$.status").value("BAD_REQUEST"));
    }

    private String createJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
