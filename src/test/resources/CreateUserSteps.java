package ovh.classregister.users.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import ovh.classregister.users.domain.User;
import ovh.classregister.users.domain.UserBody;

@ContextConfiguration
@SpringBootTest
public class CreateUserSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private UserBody userBody;

    private ResponseEntity<User> response;

    @Given("^the client has login and password \"([^\"]*)\"$")
    public void the_client_has_login_and_password(String arg1) throws Throwable {
        UserBody userBody = new ObjectMapper().readValue(arg1, UserBody.class);
        this.userBody = userBody;
    }


    @When("^the client makes a call to /users$")
    public void the_client_makes_a_call_to_users() throws Throwable {
        response = restTemplate.postForEntity("/users", userBody, User.class);
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int arg1) throws Throwable {
        assertThat(response.getStatusCode()).isEqualTo(arg1);
    }

    @Then("^the client creates new user \\$response$")
    public void the_client_creates_new_user_$response() throws Throwable {
        User user = response.getBody();
        assertThat(user.getLogin()).isEqualTo(userBody.getLogin());
        assertThat(user.getPassword()).isEqualTo(userBody.getPassword());
    }

}
