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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = UsersApplication.class)
@TestPropertySource(value = "classpath:application-test.properties")
public class DeleteUser {

    private static final int USER_ID = 2;
    private static final String NOT_FOUND = "404 Not Found";

    private RestTemplate restTemplate = new RestTemplate();


    @Given("^the user already exists$")
    public void prepareExampleUserData() throws Throwable {
        UserBody userBody = new UserBody("userLogin", "userPassword");
        restTemplate.postForEntity(CucumberConfig.URL, userBody, User.class);
    }

    @When("^the client makes a call to delete user$")
    public void deleteUser() throws Throwable {
        String url = CucumberConfig.URL + USER_ID;
        restTemplate.delete(url);
    }

    @Then("^the user has been deleted$")
    public void checkIfUserIsDeleted() throws Throwable {
        String url = CucumberConfig.URL + USER_ID;
        Throwable throwable = catchThrowable(() -> restTemplate.delete(url));
        assertThat(throwable.getMessage()).contains(NOT_FOUND);
    }
}
