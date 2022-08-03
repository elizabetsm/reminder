package telegram.ru.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import telegram.ru.boot.entity.Birthday;

@Repository
public interface BirthdayRepo extends JpaRepository<Birthday, Integer> {
//    List<BirthdaysEntity>

//    @Override
//    List<BirthdaysEntity> findAll();
}
