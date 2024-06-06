package uz.pdp.proyekt.dtos.createDtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ForgetDto {
    @NotBlank(message = "Email field cannot be empty")
    private String email;

    @NotBlank(message = "ActivationCode field cannot be empty")
    private String activationCode;

    @NotBlank(message = "New password field cannot be empty")
    private String newPassword;
}
