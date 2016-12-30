package ovh.classregister.users.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ovh.classregister.users.domain.User;
import ovh.classregister.users.domain.UserBody;
import ovh.classregister.users.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<User> getUsers(@PageableDefault final Pageable pageable) {
        return userService.getUsers(pageable);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable final long id) {
        return userService.getUser(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public User createUser(@Valid @RequestBody UserBody userBody) {
        return userService.addUser(userBody);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User editUser(@PathVariable long id, @Valid @RequestBody UserBody userBody) {
        return userService.editUser(id, userBody);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable final long id) {
        userService.deleteUser(id);
    }
}