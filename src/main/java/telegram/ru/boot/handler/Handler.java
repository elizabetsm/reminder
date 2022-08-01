package telegram.ru.boot.handler;

import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface Handler {
    BotApiMethod<?> handle(BotApiObject object);
}
