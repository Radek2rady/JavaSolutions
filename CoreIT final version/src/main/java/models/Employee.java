package models;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Employees")
public class Employee {

  @Id
  @GeneratedValue
  private int id;

  @NonNull
  private String firstName;
  @NonNull
  private String secondName;

  @Column(unique = true, name = "email")
  private String email;

  private Date lastUpdate;
}
