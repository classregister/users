import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import ovh.classregister.users.UsersApplication;
import ovh.classregister.users.domain.User;
import ovh.classregister.users.domain.UserBody;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = UsersApplication.class)
@TestPropertySource(value = "classpath:application-test.properties")
public class CreateUserErrorScenario {

    private RestTemplate restTemplate = new RestTemplate();
    private UserBody userBody;
    private Throwable response;

    @Given("^the client has empty login and password \"([^\"]*)\"$")
    public void prepareInvalidUserData(String arg1) throws Throwable {
        final String jsonBody = arg1.replace("'", "\"");
        UserBody userBody = new ObjectMapper().readValue(jsonBody, UserBody.class);
        this.userBody = userBody;
    }

    @When("^the client makes a call to create user with invalid login and password$")
    public void makeCallToCreateUserWithInvalidData() throws Throwable {
        response = catchThrowable(() -> restTemplate.postForEntity(CucumberConfig.URL, userBody, User.class));
    }

    @Then("^the client receives status (\\d+) Bad Request$")
    public void checkClientResponseStatus(int arg1) throws Throwable {
        assertThat(response.getMessage()).isEqualTo(arg1 + " Bad Request");
    }
}
