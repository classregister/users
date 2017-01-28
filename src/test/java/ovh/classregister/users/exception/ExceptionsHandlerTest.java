package ovh.classregister.users.exception;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import ovh.classregister.users.web.UserController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionsHandlerTest {

    private MockMvc mockMvc;

    @Mock
    private UserController userController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                                      .setControllerAdvice(new ExceptionsHandler())
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


}
