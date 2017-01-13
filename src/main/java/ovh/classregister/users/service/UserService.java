package ovh.classregister.users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ovh.classregister.users.domain.*;
import ovh.classregister.users.exception.ResourceNotFoundException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getUsers(final Pageable pageable) {
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

    public User editUser(final long id, UserBody userBody) {
        checkRecordId(id);
        final User user = createUser(userBody);
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        checkRecordId(id);
        userRepository.delete(id);
    }

    private User createUser(UserBody userBody) {
        User user = new User();
        user.setLogin(userBody.getLogin());
        user.setPassword(userBody.getPassword());

        return user;
    }

    private void checkRecordId(long id) {
        if(!userRepository.exists(id)) {
            throw new ResourceNotFoundException(id);
        }
    }
}
