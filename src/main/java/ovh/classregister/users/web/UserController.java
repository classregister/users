package ovh.classregister.users.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import ovh.classregister.users.domain.User;
import ovh.classregister.users.domain.UserBody;
import ovh.classregister.users.service.UserService;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Get users",
                  notes = "Get users data. Records are paginated - default page number: 1, default page "
                          + "size: 10")
    @ApiImplicitParams({
                               @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query",
                                                 value = "Results page you want to retrieve (0..N)"),
                               @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                                                 value = "Number of records per page."),
                               @ApiImplicitParam(name = "sort",
                                                 allowMultiple = true,
                                                 dataType = "string",
                                                 paramType = "query",
                                                 value = "Sorting criteria in the format: property(,asc|desc). " +
                                                         "Default sort order is ascending. " +
                                                         "Multiple sort criteria are supported.")
                       })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<User> getUsers(@ApiIgnore @PageableDefault final Pageable pageable) {
        return userService.getUsers(pageable);
    }

    @ApiOperation(value = "Get user by id",
                  notes = "Gets specific user login and password")
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable final long id) {
        return userService.getUser(id);
    }

    @ApiOperation(value = "Create user",
                  notes = "Creates new user in application")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public User createUser(@Valid @RequestBody UserBody userBody) {
        return userService.addUser(userBody);
    }

    @ApiOperation(value = "Update user by id",
                  notes = "Updates specific user login and password")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User editUser(@PathVariable long id, @Valid @RequestBody UserBody userBody) {
        return userService.editUser(id, userBody);
    }

    @ApiOperation(value = "Remove user by id",
                  notes = "Removes specific user entry")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable final long id) {
        userService.deleteUser(id);
    }
}
