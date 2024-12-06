package com.miqueiasfb.task_manager.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotBlank(message = "E-mail is mandatory")
  @Email(message = "E-mail should be valid")
  private String email;

  private String phone;

  private LocalDate birthDate;

  @NotBlank(message = "Password is mandatory")
  private String password;

  private boolean notificationsEnabled = true;
  private boolean darkMode = false;
  private String language = "pt-BR";
}
