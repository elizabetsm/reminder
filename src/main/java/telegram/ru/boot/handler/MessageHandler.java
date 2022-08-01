package telegram.ru.boot.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import telegram.ru.boot.keyboard.InlineKeyboard;
import telegram.ru.boot.keyboard.ReplyKeyboard;

/**
 * Класс обработчик для всех текстовых сообщений
 * ВСЁ, что не инлайн кнопки, считаются таковыми, даже команды начинающиеся с "/"
 * (Inline - Клавиатура привязанная к конкретному сообщению бота)
 */
@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageHandler implements Handler {

    final ReplyKeyboard replyKeyboard;
    final InlineKeyboard inlineKeyboard;

    public MessageHandler(ReplyKeyboard replyKeyboard, InlineKeyboard inlineKeyboard) {
        this.replyKeyboard = replyKeyboard;
        this.inlineKeyboard = inlineKeyboard;
    }


    @Override
    public BotApiMethod<?> handle(BotApiObject message) {
        if (message instanceof Message msg) {
            log.info("Message received with text {}", msg.getText());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(msg.getChatId().toString());
            if (msg.getText().equals("/start")) {
                sendMessage.setText("Софа , привет!!\n Пожалуйста, тыкни на кнопку и скажи боту, что ты бы хотела сделать");
                sendMessage.setReplyMarkup(replyKeyboard.getKeyboard());
            } else if (msg.getText().equals("Добавить день рождения")){
                sendMessage.setText("Пж введи дату в формате MM-DD\n это важно !!!");
            } else if (){
                //TODO сделать парсер по формату на дату и время чтобы отловить все даты
            }
            return sendMessage;
        }
        return null;
    }
}
