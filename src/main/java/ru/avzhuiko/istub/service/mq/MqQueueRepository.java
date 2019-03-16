package ru.avzhuiko.istub.service.mq;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MqQueueRepository extends JpaRepository<MqQueue, Long> {

  List<MqQueue> getAll();

  List<MqQueue> getAllByEnableIsTrueAndManager_EnableIsTrue();

}
