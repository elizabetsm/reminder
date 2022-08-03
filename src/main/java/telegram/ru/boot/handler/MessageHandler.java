package telegram.ru.boot.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import telegram.ru.boot.entity.Birthday;
import telegram.ru.boot.keyboard.InlineKeyboard;
import telegram.ru.boot.keyboard.ReplyKeyboard;
import telegram.ru.boot.repository.BirthdayRepo;

import java.util.regex.Pattern;

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
    final Pattern pattern;
    final BirthdayRepo repository;

    public MessageHandler(ReplyKeyboard replyKeyboard, InlineKeyboard inlineKeyboard, BirthdayRepo repository){
        this.replyKeyboard = replyKeyboard;
        this.inlineKeyboard = inlineKeyboard;
        this.repository = repository;
        this.pattern = Pattern.compile("\\d\\d.\\d\\d\\s\\w+");
    }

    @Override
    public BotApiMethod<?> handle(BotApiObject message) {
        if (message instanceof Message msg) {
            log.info("Message received with text {}", msg.getText());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(msg.getChatId().toString());
            if (msg.getText().equals("/start")) {
                sendMessage.setText("Софа , привет!!\nПожалуйста, тыкни на кнопку и скажи боту, что ты бы хотела сделать");
                sendMessage.setReplyMarkup(replyKeyboard.getKeyboard());
            } else if (msg.getText().equals("Добавить день рождения")){
                sendMessage.setText("Пж введи дату в формате MM-DD Фамилия и имя\nили имя фамилия\nили имя\n это важно !!!");
                sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
            } else if (msg.getText().equals("Показать все дени рождения")){
                sendMessage.setText(repository.findAll().toString());
//                sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
            } else if (pattern.matcher(msg.getText()).matches()){
                String[] str = msg.getText().split(" ");
                repository.save(new Birthday(str[0], str[1]));
                sendMessage.setText("Проверь пж, все ли верно:\n"+ msg.getText());
                sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
                sendMessage.setReplyMarkup(inlineKeyboard.getKeyboard());
            } else if (msg.getText().equals("да")) {
                sendMessage.setText("Супер, Др добавлен, напомню тебе о нем за неделю до и в день др\nчто то еще?");
                sendMessage.setReplyMarkup(replyKeyboard.getKeyboard());
            }
            return sendMessage;
        }
        return null;
    }
}
