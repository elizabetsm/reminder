package telegram.ru.boot.config;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.ru.boot.bot.SmokeBot;
import telegram.ru.boot.handler.CallbackQueryHandler;
import telegram.ru.boot.handler.MessageHandler;
import telegram.ru.boot.service.ScheduledService;


@Configuration
@EnableScheduling
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpringConfig {

    @Value("${telegram.bot.name}")
    String name;
    @Value("${telegram.bot.token}")
    String token;
    @Value("${telegram.bot.webhook}")
    String webhook;

    final ScheduledService service;

    public SpringConfig(ScheduledService service) {
        this.service = service;
    }

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(webhook).build();
    }

    @Bean
    @SneakyThrows
    public SmokeBot springWebhookBot(SetWebhook setWebhook,
                                     MessageHandler messageHandler,
                                     CallbackQueryHandler callbackQueryHandler) {
        SmokeBot bot = new SmokeBot(setWebhook,
                webhook, token, name, messageHandler, callbackQueryHandler, service);

        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(bot, setWebhook);

        return bot;
    }

}
