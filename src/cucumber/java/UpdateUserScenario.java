import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.assertj.core.api.Assertions.assertThat;
import ovh.classregister.users.UsersApplication;
import ovh.classregister.users.domain.User;
import ovh.classregister.users.domain.UserBody;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = UsersApplication.class)
@TestPropertySource(value = "classpath:application-test.properties")
public class UpdateUserScenario {

    private RestTemplate restTemplate = new RestTemplate();

    private UserBody userBody;

    private ResponseEntity<User> response;

    @Given("^the client has login and password \"([^\"]*)\" for update existing account$")
    public void prepareExampleDataForUpdate(String arg1) throws Throwable {
        final String jsonBody = arg1.replace("'", "\"");
        UserBody userBody = new ObjectMapper().readValue(jsonBody, UserBody.class);
        restTemplate.postForEntity(CucumberConfig.URL, userBody, User.class);
        this.userBody = userBody;
    }

    @When("^the client makes a call to update user$")
    public void updateUserData() throws Throwable {
        userBody = new UserBody("userLoginUpdated", "userPassword");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<UserBody> httpEntity = new HttpEntity<>(userBody, httpHeaders);
        int id = 7;
        final String url = CucumberConfig.URL + id;
        response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, User.class);
    }

    @Then("^the client receives status code of (\\d+) - successful update$")
    public void checkStatusCode(int arg1) throws Throwable {
        assertThat(response.getStatusCode()
                           .value()).isEqualTo(arg1);
    }

    @Then("^the client updates a user with proper \\$values$")
    public void checkIfUserIsProperlyUpdated() throws Throwable {
        User user = response.getBody();
        assertThat(user.getLogin()).isEqualTo(userBody.getLogin());
        assertThat(user.getPassword()).isEqualTo(userBody.getPassword());
    }
}
