import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.assertj.core.api.Assertions.assertThat;
import ovh.classregister.users.UsersApplication;
import ovh.classregister.users.domain.User;
import ovh.classregister.users.domain.UserBody;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = UsersApplication.class)
@TestPropertySource(value = "classpath:application-test.properties")
public class CreateUser {

    private final RestTemplate restTemplate = new RestTemplate();

    private UserBody userBody;

    private ResponseEntity<User> response;

    @Given("^the client has login and password \"([^\"]*)\"$")
    public void prepareUserData(String arg1) throws Throwable {
        final String jsonBody = arg1.replace("'", "\"");
        UserBody userBody = new ObjectMapper().readValue(jsonBody, UserBody.class);
        this.userBody = userBody;
    }

    @When("^the client makes a call to create user$")
    public void createUser() throws Throwable {
        response = restTemplate.postForEntity(CucumberConfig.URL, userBody, User.class);
    }

    @Then("^the client receives status code of (\\d+)$")
    public void checkResponseStatusCode(int arg1) throws Throwable {
        assertThat(response.getStatusCode().value()).isEqualTo(arg1);
    }

    @Then("^the client creates a new user with proper \\$values$")
    public void checkIfUserIsProperlyCreated() throws Throwable {
        User user = response.getBody();
        assertThat(user.getLogin()).isEqualTo(userBody.getLogin());
        assertThat(user.getPassword()).isEqualTo(userBody.getPassword());
    }
}
