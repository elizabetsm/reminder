package telegram.ru.boot.service;

import org.springframework.stereotype.Service;
import telegram.ru.boot.entity.Birthday;
import telegram.ru.boot.keyboard.ReplyKeyboard;
import telegram.ru.boot.repository.BirthdayRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    public Map<Long, List<Birthday>> findDailyNotifs() {

        //todo заменить на запрос к базе по месяцу
        List<Birthday> list = repository.findAll().stream()
                .filter(birthday -> birthday.getBirthday().getMonth().equals(LocalDate.now().getMonth()))
                .toList();


        // предусмотрено, что у одного юзера может быть несколько напоминаний в один день
        Map<Long, List<Birthday>> listMap = new HashMap<>();
        list.forEach(birthday -> {
            Long id = Long.parseLong(birthday.getChatId());
            if (!listMap.containsKey(id)) {
                listMap.put(id, new ArrayList<>());
            }
            listMap.get(id).add(birthday);
        });
        return listMap;

    }
}
