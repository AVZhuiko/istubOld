package ru.avzhuiko.istub.service.user;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "token")
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, unique = true)
  private String token;

  /**
   * Date to which the token is valid
   */
  @Column(nullable = false)
  private Date expiredAt;

  @Column(nullable = false)
  private boolean unlimited = false;

  /**
   * The user who owns the token
   */
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private User user;

}
