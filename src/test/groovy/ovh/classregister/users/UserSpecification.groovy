package ovh.classregister.users

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import ovh.classregister.users.exception.ProblemControllerAdvice
import spock.lang.Specification

class UserSpecification extends Specification {

	def setup() {
		def pageableResolver = new PageableHandlerMethodArgumentResolver()
		def exceptionHandler = new ProblemControllerAdvice()

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(UserControllerSetup.createUserController())
		                                 .setControllerAdvice(exceptionHandler)
		                                 .setCustomArgumentResolvers(pageableResolver)
		                                 .build()

		RestAssuredMockMvc.mockMvc(mockMvc)
	}
}
