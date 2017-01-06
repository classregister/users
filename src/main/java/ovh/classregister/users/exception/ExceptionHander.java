package ovh.classregister.users.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.net.URI;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@ControllerAdvice
public class ExceptionHander implements ProblemHandling {

    private static final String DETAIL = "User with id: %d not found";

    private static final String TITLE = "User does not exist";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Problem> handleResourceNotFoundException(ResourceNotFoundException e) {
        long id = e.getId();

        Problem problem = Problem.builder()
                                 .withType(URI.create("/users/" + id))
                                 .withDetail(String.format(DETAIL, id))
                                 .withStatus(NOT_FOUND)
                                 .withTitle(TITLE)
                                 .build();

        return new ResponseEntity<>(problem, HttpStatus.NOT_FOUND);
    }
}
