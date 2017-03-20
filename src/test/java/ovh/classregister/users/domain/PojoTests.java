package ovh.classregister.users.domain;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;
import pl.pojo.tester.api.assertion.Method;

import org.junit.Test;

public class PojoTests {

    @Test
    public void shouldTestUserClass() {
        // given
        final Class<?> classesUnderTests = User.class;

        // when

        // then
        assertPojoMethodsForAll(classesUnderTests).areWellImplemented();
    }

    @Test
    public void shouldTestUserBodyClass() {
        // given
        final Class<?> classesUnderTests = UserBody.class;

        // when

        // then
        assertPojoMethodsFor(classesUnderTests).testing(Method.CONSTRUCTOR)
                                               .testing(Method.GETTER)
                                               .areWellImplemented();
    }
}
