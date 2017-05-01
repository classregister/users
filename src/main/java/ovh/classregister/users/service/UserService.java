package ovh.classregister.users.service;

import ovh.classregister.users.domain.User;
import ovh.classregister.users.domain.UserBody;
import ovh.classregister.users.domain.UserRepository;
import ovh.classregister.users.exception.ResourceNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUser(long id) {
        checkRecordId(id);
        return userRepository.findOne(id);
    }

    public User addUser(UserBody userBody) {
        final User user = createUser(userBody);
        return userRepository.save(user);
    }

    public User editUser(long id, UserBody userBody) {
        checkRecordId(id);
        final User user = createUser(userBody);
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        checkRecordId(id);
        userRepository.delete(id);
    }

    public User searchForUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    private User createUser(UserBody userBody) {
        User user = new User();
        user.setLogin(userBody.getLogin());
        user.setPassword(userBody.getPassword());

        return user;
    }

    private void checkRecordId(long id) {
        if (!userRepository.exists(id)) {
            throw new ResourceNotFoundException(id);
        }
    }
}
