package uz.pdp.proyekt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.dtos.createDtos.MailDto;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    public void sendMail(MailDto dto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(dto.getEmail());
        simpleMailMessage.setText(dto.getMessage());
        mailSender.send(simpleMailMessage);
    }

}
