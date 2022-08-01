package telegram.ru.boot.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Класс обработчик для апдейтов прилетающих с кнопок с клавиатуры
 */
@Component
@Slf4j
public class CallbackQueryHandler implements Handler {

    @Override
    public BotApiMethod<?> handle(BotApiObject callbackQuery) {
        if (callbackQuery instanceof CallbackQuery query) {
            Message msg = query.getMessage();
            log.info("CallbackQuery received with text {}", msg.getText());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(msg.getChatId().toString());
            sendMessage.setText(query.getData());
            return sendMessage;
        }
        return null;
    }
}