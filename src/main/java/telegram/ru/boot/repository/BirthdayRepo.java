package telegram.ru.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import telegram.ru.boot.entity.Birthday;

import java.util.List;

@Repository
public interface BirthdayRepo extends JpaRepository<Birthday, Integer> {
    void deleteBirthdayByName(String name);

    @Override
    List<Birthday> findAll();
}
