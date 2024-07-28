package com.riwi.filtro.api.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

  @NotEmpty(message = "Invalid name")
  @Length(min = 3, max = 100, message = "Length of the name is invalid")
  private String name;

  @NotBlank(message = "The userName is required")
  @Length(min = 3, max = 20, message = "Invalid userName")
  private String userName;


  @NotEmpty(message = "Empty email")
  @Length(min = 5, max = 100, message = "Length of the email is invalid")
  @Email(message = "The email address is invalid")
  private String email;

  @NotEmpty(message = "Empty password")
  @Length(min = 10, max = 255, message = "Length of the password is invalid")
  private String password;

  @NotNull(message = "The status of the user is required")
  private boolean active;
}
