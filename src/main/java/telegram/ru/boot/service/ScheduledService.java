package telegram.ru.boot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import telegram.ru.boot.entity.Birthday;
import telegram.ru.boot.keyboard.ReplyKeyboard;
import telegram.ru.boot.repository.BirthdayRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Класс для подготовки ежедневной рассылки напоминаний о др
 */
@Service
public class ScheduledService {

    final BirthdayRepo repository;
    final ReplyKeyboard replyKeyboard;

    public ScheduledService(BirthdayRepo repository, ReplyKeyboard replyKeyboard) {
        this.repository = repository;
        this.replyKeyboard = replyKeyboard;
    }

    public Map<Integer, Birthday> findDailyNotifs(){
       List<Birthday> list = repository.findAll().stream()
               .filter(birthday -> birthday.getBirthday().getMonth().equals(LocalDate.now().getMonth()))
               .toList();
       return list.stream().filter(birthday -> birthday.getBirthday().getDayOfMonth() == LocalDate.now().getDayOfMonth())
               .collect(Collectors.toMap(Birthday::getId, birthday -> birthday));

    }

    public BotApiMethod<?> sendNotif(Map<Integer, Birthday> birthdayList){

//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(birthdayList.get(1).getId().toString());
//        sendMessage.setText("Список на сегодня");
//        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
//        sendMessage.setReplyMarkup(replyKeyboard.getKeyboard());
    }
}
