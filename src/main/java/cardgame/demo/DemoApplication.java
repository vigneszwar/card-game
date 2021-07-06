package cardgame.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"cardgame.service",
                        "cardgame.schema"})
@ComponentScan(basePackages = {"cardgame", "cardgame.service", "cardgame.schema"})
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);

    }

}
