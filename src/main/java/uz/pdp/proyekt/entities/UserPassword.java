package uz.pdp.proyekt.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Passwords")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPassword extends BaseEntity{
    private String code;
    @ManyToOne
    private UserEntity user;
    private LocalDateTime sentDate;
    private Integer expiry;
}
