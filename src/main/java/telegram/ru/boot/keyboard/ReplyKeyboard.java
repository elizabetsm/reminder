package telegram.ru.boot.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboard {

    public ReplyKeyboardMarkup getKeyboard() {

        // формируем клаву построчно
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Добавить день рождения"));
        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Удалить день рождения"));
        KeyboardRow row3 = new KeyboardRow();
        row2.add(new KeyboardButton("Показать все дени рождения"));

        // Объединяем строки клавиатуры в однин массив
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(row1);
        keyboardRows.add(row2);
        keyboardRows.add(row3);

        // Создаем объект клавиатуры из массива и дополнительно настраиваем, если надо
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setSelective(true);

        return replyKeyboardMarkup;
    }
}
