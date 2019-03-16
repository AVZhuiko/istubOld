package ru.avzhuiko.istub.service.mq;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MqManagerRepository extends JpaRepository<MqManager, Long> {

  List<MqManager> getAll();

  List<MqManager> getAllByEnableIsTrue();

}
