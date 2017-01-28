package ovh.classregister.users.domain;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @BeforeTransaction
    public void setUpUserRepository() {
        userRepository.save(prepareUsers());
    }

    @Test
    public void shouldGetAllUsers() {
        // given
        Pageable pageable = new PageRequest(0, 3);
        List<User> expectedResult = prepareUsers();

        // when
        Page<User> result = userRepository.findAll(pageable);

        // then
        assertThat(result.getContent()).hasSize(expectedResult.size());
        assertThat(result.getContent()).usingFieldByFieldElementComparator()
                                       .isEqualTo(expectedResult);
    }

    @Test
    public void shouldGetUsersWithinRange() {
        // given
        Pageable pageable = new PageRequest(1, 2);
        List<User> expectedResult = newArrayList(createUser(3, "user3", "password"));

        // when
        Page<User> result = userRepository.findAll(pageable);

        // then
        assertThat(result.getContent()).hasSize(expectedResult.size());
        assertThat(result.getContent()).usingFieldByFieldElementComparator()
                                       .isEqualTo(expectedResult);
    }

    @Test
    public void shouldGetUser() {
        // given
        long userId = 1L;
        User expectedResult = createUser(userId, "user", "password");

        // when
        User result = userRepository.findOne(userId);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    public void shouldAddUser() {
        // given
        long userId = 4L;
        User user = createUser(0, "user4", "password");
        User expectedUser = createUser(userId, "user4", "password");

        // when
        User result = userRepository.save(user);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedUser);
    }

    @Test
    public void shouldUpdateUser() {
        // given
        long userId = 2L;
        User user = createUser(userId, "user2", "password2");
        User expectedUser = createUser(userId, "user2", "password2");

        // when
        User result = userRepository.save(user);

        // then
        assertThat(result).isEqualToComparingFieldByField(expectedUser);
    }

    @Test
    public void shouldDeleteUser() {
        // given
        long userId = 1L;
        List<User> expectedResult = prepareUsers().subList(1, 3);

        // when
        userRepository.delete(userId);

        // then
        assertThat(userRepository.findAll()).usingRecursiveFieldByFieldElementComparator()
                                            .isEqualTo(expectedResult);
    }

    private List<User> prepareUsers() {
        User user = createUser(1, "user", "password");
        User user2 = createUser(2, "user2", "password");
        User user3 = createUser(3, "user3", "password");
        return newArrayList(user, user2, user3);
    }

    private User createUser(long id, String login, String password) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);

        return user;
    }
}
