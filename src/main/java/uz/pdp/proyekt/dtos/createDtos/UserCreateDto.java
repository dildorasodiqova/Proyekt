package uz.pdp.proyekt.dtos.createDtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateDto {
    @NotBlank(message = "Email field cannot be empty")
    private String name;

    @NotBlank(message = "Username field cannot be empty")
    @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters long ")
    private String username;

    @NotBlank(message = "Email field cannot be empty")
    @Email(message = "A valid email address must be entered")
    @Pattern(regexp = "^[\\w.-]+@gmail\\.uz$", message = "Email address must end with gmail.com")
    private String email;

    @NotBlank(message = "Password field cannot be empty")
    private String password;

}
