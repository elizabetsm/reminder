package telegram.ru.boot.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Table(name = "birthdays")
@Getter
@Setter
@NoArgsConstructor
public class Birthday {

    public Birthday(String name, LocalDate birthday, String chatId) {
        this.birthday = birthday;
        this.name = name;
        this.chatId = chatId;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "teleg_name")
    private String chatId;
}