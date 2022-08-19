package telegram.ru.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import telegram.ru.boot.entity.Birthday;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BirthdayRepo extends JpaRepository<Birthday, Long> {

    @Transactional
    void deleteBirthdayByName(String name);

    @Override
    List<Birthday> findAll();
}
