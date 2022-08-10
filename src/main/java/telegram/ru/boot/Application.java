package telegram.ru.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.Scheduled;
import telegram.ru.boot.service.ScheduledService;

@SpringBootApplication
@Slf4j
@EnableJpaRepositories
public class Application {

//    final ScheduledService service;
//
//    public Application(ScheduledService service) {
//        this.service = service;
//    }

    public static void main(String[] args) {
        log.info("Bot on startup");
        SpringApplication.run(Application.class, args);
        log.info("Bot started!!!");
    }

//    @Scheduled(cron = "@daily")
//    public void checkDayNotifs(){
//        service.sendNotif();
//    }
}
