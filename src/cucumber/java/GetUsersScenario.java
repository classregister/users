import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.assertj.core.api.Assertions.assertThat;
import ovh.classregister.users.UsersApplication;
import ovh.classregister.users.domain.User;
import ovh.classregister.users.domain.UserBody;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = UsersApplication.class)
@TestPropertySource(value = "classpath:application-test.properties")
public class GetUsersScenario {

    private static final String USER_LOGIN = "userLogin3";
    private static final String USER_PASSWORD = "userPassword3";
    private static final int TOTAL_ELEMENTS = 5;
    private final RestTemplate restTemplate = new RestTemplate();

    private ResponseEntity<Object> response;

    @Given("^the example users data$")
    public void preparedExampleUsersData() throws Throwable {
        UserBody userBody = new UserBody(USER_LOGIN, USER_PASSWORD);
        restTemplate.postForEntity(CucumberConfig.URL, userBody, User.class);
        restTemplate.postForEntity(CucumberConfig.URL, userBody, User.class);
        restTemplate.postForEntity(CucumberConfig.URL, userBody, User.class);
    }

    @When("^the client makes a call to get users")
    public void getUsersData() throws Throwable {
        HttpEntity<Object> httpEntity = null;
        response = restTemplate.exchange(CucumberConfig.URL, HttpMethod.GET, httpEntity, Object.class);
    }

    @Then("^the client receives list of users$")
    public void checkReceivedDataFromResponse() throws Throwable {
        Map<String, Object> result = (LinkedHashMap<String, Object>) response.getBody();
        List<User> content = (List<User>) result.get("content");
        assertThat(content).hasSize(TOTAL_ELEMENTS);
    }
}
