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

import java.time.LocalDate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    final Pattern delPattern;
    final BirthdayRepo repository;

    public MessageHandler(ReplyKeyboard replyKeyboard, InlineKeyboard inlineKeyboard, BirthdayRepo repository){
        this.replyKeyboard = replyKeyboard;
        this.inlineKeyboard = inlineKeyboard;
        this.delPattern = Pattern.compile("\\w+");
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
                sendMessage.setText(msg.getChat().getFirstName() +" , привет!!\nПожалуйста, тыкни на кнопку и скажи боту, что ты бы хотела сделать");
                sendMessage.setReplyMarkup(replyKeyboard.getKeyboard());
            } else if (msg.getText().equals("Добавить день рождения")){
                sendMessage.setText("Пж введи дату в формате MM-DD Фамилия и имя\nили имя фамилия\nили имя\nэто важно !!!");
                sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
            } else if (msg.getText().equals("Показать все дени рождения")){
                String res = repository.findAll().stream()
                        .map(n -> {
                            //TODO разобраться че куда , точно ли здесь е стринга нужна
                            LocalDate bday = n.getBirthday();
                            String name = n.getName();
                            return bday + " " + name;
                        })
                        .collect(Collectors.joining("\n"));
                sendMessage.setText(res);
            } else if (msg.getText().equals("Удалить день рождения")) {
                sendMessage.setText("Напиши имя , кого удалить");
            } else if (pattern.matcher(msg.getText()).matches()){
                //парсим строку по пробела чтобы потом размапить в энтитю
                String[] str = msg.getText().split(" ");
                // LocalDate.parse(str[1]) здесь для того, чтобы лучше работалось со временеи с будущем
                repository.save(new Birthday(str[1],
                        LocalDate.parse("2000-"+str[0]),
                        msg.getChatId().toString()));
                sendMessage.setText("Проверь пж, все ли верно:\n"+ msg.getText());
                sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
                sendMessage.setReplyMarkup(inlineKeyboard.getKeyboard());
            } else if (delPattern.matcher(msg.getText()).matches()) {
                repository.deleteBirthdayByName(msg.getText());
                sendMessage.setText("уДоЛеНо");
                sendMessage.setReplyMarkup(replyKeyboard.getKeyboard());
            }
                return sendMessage;
        }
        return null;
    }
}
