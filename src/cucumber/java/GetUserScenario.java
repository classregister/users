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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = UsersApplication.class)
@TestPropertySource(value = "classpath:application-test.properties")
public class GetUserScenario {

    private static final String USER_LOGIN = "userLogin3";
    private static final String USER_PASSWORD = "userPassword3";
    private static final int USER_ID = 3;
    private final RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<User> response;

    @Given("^the example user data$")
    public void preparedExampleUserData() throws Throwable {
        UserBody userBody = new UserBody(USER_LOGIN, USER_PASSWORD);
        restTemplate.postForEntity(CucumberConfig.URL, userBody, User.class);
    }

    @When("^the client makes a call to get user data$")
    public void getUserData() throws Throwable {
        String url = CucumberConfig.URL + USER_ID;
        response = restTemplate.getForEntity(url, User.class);
    }

    @Then("^the client receives a user with proper values$")
    public void checkReceivedDataFromResponse() throws Throwable {
        User result = response.getBody();
        assertThat(result.getId()).isEqualTo(USER_ID);
        assertThat(result.getLogin()).isEqualTo(USER_LOGIN);
        assertThat(result.getPassword()).isEqualTo(USER_PASSWORD);
    }
}
