package com.tenpo.challenge.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI challengeOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Tenpo challenge")
                        .description("API Rest")
                        .version("v1.0.0")
                        .license(new License().name("GNU General Public License").url("https://www.gnu.org/licenses/gpl-3.0.html"))
                        .contact(new Contact().name("Christian Rovera").email("christianrovera@gmail.com")));
    }
}
