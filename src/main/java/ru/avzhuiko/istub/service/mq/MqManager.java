package ru.avzhuiko.istub.service.mq;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

/**
 * Class for persist manager parameters
 */
@Setter
@Getter
@Entity
@Table(
    name = "mq_manager",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "host"),
        @UniqueConstraint(columnNames = "port"),
        @UniqueConstraint(columnNames = "manager"),
        @UniqueConstraint(columnNames = "channel")
    }
)
public class MqManager {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /**
   * The host where the manager is located
   */
  @Column(nullable = false)
  private String host;

  /**
   * The port where the manager is located
   */
  @Column(nullable = false)
  private int port;

  /**
   * Name of manager
   */
  @Column(nullable = false)
  private String manager;

  /**
   * Name of channel
   */
  @Column(nullable = false)
  private String channel;

  /**
   * Username for login on channel
   */
  @Column
  private String username;

  /**
   * Password for login on channel
   */
  @Column
  private String password;

  /**
   * Enable this manager
   */
  @Column(nullable = false)
  private boolean enable;

}
