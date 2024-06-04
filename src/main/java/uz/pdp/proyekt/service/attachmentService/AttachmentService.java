package uz.pdp.proyekt.service.attachmentService;

import org.springframework.web.multipart.MultipartFile;
import uz.pdp.proyekt.entities.AttachmentEntity;

import java.io.IOException;
import java.util.UUID;

public interface AttachmentService {
    UUID uploadImage(MultipartFile file) throws IOException;
    AttachmentEntity downloadImage(UUID fileId);
}
