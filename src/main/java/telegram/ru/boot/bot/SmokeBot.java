package telegram.ru.boot.bot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import telegram.ru.boot.config.SpringConfig;
import telegram.ru.boot.handler.CallbackQueryHandler;
import telegram.ru.boot.handler.Handler;
import telegram.ru.boot.handler.MessageHandler;

import java.io.IOException;

/**
 * класс для использования в {@link SpringConfig#springWebhookBot(SetWebhook, MessageHandler, CallbackQueryHandler)}
 */
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmokeBot extends SpringWebhookBot {

    String webhook;
    String token;
    String name;

    // два некоторых обработчика
    Handler messageHandler; //собщение словами (текстом, буквами некими)
    Handler callbackQueryHandler;//инлайн клава

    public SmokeBot(SetWebhook setWebhook, String webhook, String token, String name, MessageHandler messageHandler,
                    CallbackQueryHandler callbackQueryHandler) {
        super(setWebhook);
        this.webhook = webhook;
        this.token = token;
        this.name = name;
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }

    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update);
        } catch (IllegalArgumentException e) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "BotMessageEnum.EXCEPTION_ILLEGAL_MESSAGE.getMessage()");
        } catch (Exception e) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    "BotMessageEnum.EXCEPTION_WHAT_THE_FUCK.getMessage()");
        }
    }

    private BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.handle(callbackQuery);
        } else {
            Message message = update.getMessage();
            if (message != null) {
                return messageHandler.handle(update.getMessage());
            }
        }
        return null;
    }

    @Override
    public String getBotPath() {
        return null;
    }
}
