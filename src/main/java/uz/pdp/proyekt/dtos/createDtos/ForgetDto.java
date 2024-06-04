package uz.pdp.proyekt.dtos.createDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ForgetDto {
    private String email;
    private String activationCode;
    private String newPassword;
}
