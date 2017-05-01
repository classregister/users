package ovh.classregister.users.exception;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.net.URI;

@ControllerAdvice
public class ProblemControllerAdvice implements ProblemHandling {

    private static final String INVALID_USER_DETAIL = "User with id: %d not found";

    private static final String INVALID_USER_TITLE = "User does not exist";

    private static final String DATA_INTEGRITY_VIOLATION_TITLE = "Invalid login";

    private static final String DATA_INTEGRITY_VIOLATION_DETAIL = "Login already exists. Login must be unique";

    private static final Logger LOGGER = LoggerFactory.getLogger(ProblemControllerAdvice.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Problem> handleResourceNotFoundException(ResourceNotFoundException e) {
        long id = e.getId();
        LOGGER.warn("Resource not found - id: " + id, e);

        Problem problem = Problem.builder()
                                 .withType(URI.create("/users/" + id))
                                 .withDetail(String.format(INVALID_USER_DETAIL, id))
                                 .withStatus(NOT_FOUND)
                                 .withTitle(INVALID_USER_TITLE)
                                 .build();

        return new ResponseEntity<>(problem, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Problem> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        LOGGER.error("Data integrity violation - invalid login", e);

        Problem problem = Problem.builder()
                                 .withDetail(DATA_INTEGRITY_VIOLATION_DETAIL)
                                 .withStatus(BAD_REQUEST)
                                 .withTitle(DATA_INTEGRITY_VIOLATION_TITLE)
                                 .build();

        return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
    }
}
