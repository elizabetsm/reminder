package telegram.ru.boot.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.ru.boot.keyboard.ReplyKeyboard;

/**
 * Класс обработчик для апдейтов прилетающих с кнопок с клавиатуры
 */
@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CallbackQueryHandler implements Handler {

    final ReplyKeyboard replyKeyboard;

    public CallbackQueryHandler(ReplyKeyboard replyKeyboard) {
        this.replyKeyboard = replyKeyboard;
    }

    @Override
    public BotApiMethod<?> handle(BotApiObject callbackQuery) {

        if (callbackQuery instanceof CallbackQuery query) {
            Message msg = query.getMessage();
            log.info("CallbackQuery received with text {} and query data {}", msg.getText(), query.getData());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(msg.getChatId().toString());

            if (query.getData().equals("y")) {
                sendMessage.setText("Супер, Др добавлен, напомню тебе о нем за неделю до и в день др\nчто то еще?");
                sendMessage.setReplyMarkup(replyKeyboard.getKeyboard());
            } else {
                sendMessage.setText("Тада еще раз\nПж введи дату в формате MM-DD Фамилия и имя\nили имя фамилия\nили имя\nэто важно !!!");
            }

            return sendMessage;
        }
        return null;
    }
}