package telegram.ru.boot.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "birthdays")
@Getter
@Setter
@NoArgsConstructor
public class Birthday {

    public Birthday(String name, String birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birthday", nullable = false)
    private String birthday;
}