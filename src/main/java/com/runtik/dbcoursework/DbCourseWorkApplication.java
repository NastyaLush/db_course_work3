package com.runtik.dbcoursework;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

@SpringBootApplication
@SecurityScheme(name = "javainuseapi", in = HEADER, type = HTTP, scheme = "bearer", bearerFormat = "JWT")
public class DbCourseWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbCourseWorkApplication.class, args);
    }

}
