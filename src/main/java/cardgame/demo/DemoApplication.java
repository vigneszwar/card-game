package cardgame.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"cardgame.services",
        "cardgame.schemas"})
@ComponentScan(basePackages = {"cardgame", "cardgame.services", "cardgame.schemas"})
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

    }

}
