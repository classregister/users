package ovh.classregister.users.domain;

import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsForAll;

public class PojoTests {

    @Test
    public void shouldPassAllPojoTests() {
        // given
        final Class<?> classesUnderTests = User.class;

        // when

        // then
        assertPojoMethodsForAll(classesUnderTests).areWellImplemented();
    }

    @Test
    public void shouldPassGetterAndContructorTests() {
        // given
        final Class<?> classesUnderTests = UserBody.class;

        // when

        // then
        assertPojoMethodsFor(classesUnderTests).testing(Method.CONSTRUCTOR)
                                               .testing(Method.GETTER)
                                               .areWellImplemented();
    }
}
