package ovh.classregister.users.configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableSwagger2
@Configuration
@ConditionalOnProperty(value="swagger.enabled")
public class SwaggerConfiguration {

    private static final String PATH_REGEX = "/users.*";


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.any())
                                                      .paths(PathSelectors.regex(PATH_REGEX))
                                                      .build()
                                                      .apiInfo(createApiInfo());
    }

    private ApiInfo createApiInfo() {
        Contact contact = new Contact("Krzysztof Janiec", "http://classregister.ovh", "krzysztof.janiec@poczta.pl");

        return new ApiInfoBuilder().version("1.0")
                                   .description("Users api documentation for class register application")
                                   .contact(contact)
                                   .title("Users api documentation")
                                   .build();
    }
}
