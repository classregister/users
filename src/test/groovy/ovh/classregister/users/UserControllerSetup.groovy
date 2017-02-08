package ovh.classregister.users

import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import ovh.classregister.users.domain.User
import ovh.classregister.users.domain.UserBody
import ovh.classregister.users.exception.ResourceNotFoundException
import ovh.classregister.users.service.UserService
import ovh.classregister.users.web.UserController

import static org.mockito.Matchers.any
import static org.mockito.Matchers.anyLong
import static org.mockito.Mockito.*

class UserControllerSetup {

	private static final long USER_ID = 1
	private static final String USER_LOGIN = "user_login"
	private static final String USER_PASSWORD = "user_password"

	static UserController createUserController() {
		UserService userService = mock(UserService.class)
		UserController userController = new UserController(userService)

		doReturn(createPagedUsers()).when(userService)
		                            .getUsers(any() as Pageable)

		doReturn(createUser()).when(userService)
		                      .getUser(1)

		doThrow(new ResourceNotFoundException(2)).when(userService)
		                                         .getUser(2)

		doReturn(createUser()).when(userService)
		                      .addUser(any() as UserBody)

		doThrow(new ResourceNotFoundException(2)).when(userService)
		                                         .deleteUser(2)

		doAnswer(userUpdateAnswer()).when(userService)
		                            .editUser(anyLong(), any(UserBody.class))

		return userController
	}

	private static Page<User> createPagedUsers() {
		List<User> users = [createUser()]
		Pageable pageable = new PageRequest(1, 10)
		long totalElements = 1

		new PageImpl<User>(users, pageable, totalElements)
	}

	private static User createUser() {
		new User(id: USER_ID, login: USER_LOGIN, password: USER_PASSWORD)
	}

	private static Answer<User> userUpdateAnswer() {
		new Answer<User>() {
			@Override
			User answer(final InvocationOnMock invocation) throws Throwable {
				Object[] arguments = invocation.getArguments()
				long id = (long) arguments[0]
				if (id == 1L) {
					return createUser()
				}

				throw new ResourceNotFoundException(2)
			}
		}

	}
}
