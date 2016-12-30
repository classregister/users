package ovh.classregister.users.service;

import org.junit.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ovh.classregister.users.domain.User;
import ovh.classregister.users.domain.UserBody;
import ovh.classregister.users.domain.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

public class UserServiceTests {

    private static UserRepository userRepository;

    private static UserService userService;

    @BeforeClass
    public static void initService() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
        setInternalState(userService, "userRepository", userRepository);
    }

    @Test
    public void shouldGetUsersFromRepository() {
        // given
        Pageable pageable = new PageRequest(0, 2);

        // when
        userService.getUsers(pageable);

        // then
        verify(userRepository).findAll(pageable);
    }

    @Test
    public void shouldGetUserFromRepository() {
        // given
        long userId = 1;
        doReturn(true).when(userRepository).exists(userId);

        // when
        userService.getUser(userId);

        // then
        verify(userRepository).findOne(userId);
    }

    @Test
    public void shouldThrowExceptionWhenGetUserWithInvalidId() {
        // given
        long userId = 1;
        doReturn(false).when(userRepository).exists(userId);

        // when
        Throwable result = (catchThrowable(() -> userService.getUser(userId)));

        // then
        assertThat(result).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void shouldAddUserToRepository() {
        // given
        UserBody userBody = new UserBody("user", "password");
        User expectedUser = createUser(0, "user", "password");

        // when
        userService.addUser(userBody);

        // then
        verify(userRepository).save(expectedUser);
    }

    @Test
    public void shouldUpdateUserInRepository() {
        // given
        long userId = 1;
        UserBody userBody = new UserBody("user", "password");
        User expectedUser = createUser(userId, "user", "password");
        doReturn(true).when(userRepository).exists(userId);

        // when
        userService.editUser(userId, userBody);

        // then
        verify(userRepository).save(expectedUser);
    }

    @Test
    public void shouldThrowExceptionWhenEditUserWithInvalidId() {
        // given
        long userId = 1;
        UserBody userBody = new UserBody("user", "password");
        doReturn(false).when(userRepository).exists(userId);

        // when
        Throwable result = (catchThrowable(() -> userService.editUser(userId, userBody)));

        //then
        assertThat(result).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    public void shouldDeleteUserInRepository() {
        // given
        long userId = 1;
        doReturn(true).when(userRepository).exists(userId);

        // when
        userService.deleteUser(userId);

        // then
        verify(userRepository).delete(userId);
    }

    @Test
    public void shouldThrowExceptionWhenDeleteUserWithInvalidId() {
        // given
        long userId = 1;
        doReturn(false).when(userRepository).exists(userId);

        // when
        Throwable result = (catchThrowable(() -> userService.deleteUser(userId)));

        // then
        assertThat(result).isInstanceOf(EmptyResultDataAccessException.class);
    }

    private User createUser(long id, String login, String password) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);

        return user;
    }
}
