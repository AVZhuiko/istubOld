package ru.avzhuiko.istub.service.mq;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

/**
 * Class for persist queue parameters
 */
@Setter
@Getter
@Entity
@Table(
    name = "mq_queue",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "manager"),
        @UniqueConstraint(columnNames = "name"),
        @UniqueConstraint(columnNames = "reader")
    }
)
public class MqQueue {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /**
   * Manager to connect to the queue
   */
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private MqManager manager;

  /**
   * Name of Queue
   */
  @Column(nullable = false)
  private String name;

  /**
   * Type of Queue. true - consumer, false - provider
   */
  @Column(nullable = false)
  private boolean reader;

  /**
   * Enable this queue
   */
  @Column(nullable = false)
  private boolean enable;

}
