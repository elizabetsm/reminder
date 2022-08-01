package telegram.ru.boot.controller;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.ru.boot.bot.SmokeBot;


/**
 * Класс для получения всех апдейтов от телеграма, то есть от юзера, который пишет боту
 */
@Slf4j
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebhookController {

    SmokeBot bot;

    public WebhookController(SmokeBot webHookConfig) {
        this.bot = webHookConfig;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdate(@RequestBody Update update) {
        log.info("Update received");
        return bot.onWebhookUpdateReceived(update);
    }
}
