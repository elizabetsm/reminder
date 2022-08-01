package telegram.ru.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application {

    public static void main(String[] args) {
        log.info("Bot on startup");
        SpringApplication.run(Application.class, args);
        log.info("Bot started!!!");
    }
}
