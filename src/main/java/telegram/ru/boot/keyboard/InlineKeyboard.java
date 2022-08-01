package telegram.ru.boot.keyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для формирования инлайн клавиатуры, то есть той, что присылается вместе
 * с ответным сообщением в боте
 */
@Component
public class InlineKeyboard {

    public InlineKeyboardMarkup getKeyboard() {

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(getOneButton("inline 1"));
        rowList.add(getOneButton("inline 2"));
        rowList.add(getOneButton("inline 3"));

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
//
        return inlineKeyboardMarkup;
    }


    List<InlineKeyboardButton> getOneButton(String text) {
        List<InlineKeyboardButton> one = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData("You pressed button with text " + text);
        one.add(button);
        return one;
    }
}
