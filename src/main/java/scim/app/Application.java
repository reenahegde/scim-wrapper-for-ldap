package scim.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author AkshathaKadri
 *
 */
@SpringBootApplication
@ComponentScan({"scim"})
@EntityScan("scim")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
