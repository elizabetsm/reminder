package telegram.ru.boot.service;

import org.jvnet.hk2.annotations.Service;
import telegram.ru.boot.entity.Birthday;
import telegram.ru.boot.repository.BirthdayRepo;

import java.util.List;

@Service
public class ScheduledService {

    final BirthdayRepo repository;

    public ScheduledService(BirthdayRepo repository) {
        this.repository = repository;
    }

//    public void sendNotif(){
//       List<Birthday> = repository.findAll();
//    }
}
