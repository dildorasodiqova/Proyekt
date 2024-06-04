package uz.pdp.proyekt.service.attachmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.proyekt.entities.AttachmentEntity;
import uz.pdp.proyekt.exception.DataNotFoundException;
import uz.pdp.proyekt.repositories.AttachmentRepository;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Override
    public UUID uploadImage(MultipartFile file) throws IOException {
        AttachmentEntity attachment = attachmentRepository.save(AttachmentEntity.builder()
                .name(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .bytes(file.getBytes()).build());

        return attachment.getId();

    }

    @Override
    public AttachmentEntity downloadImage(UUID fileId) throws RuntimeException {
        return attachmentRepository.findById(fileId).orElseThrow(()->new DataNotFoundException("Attachment not found"));
    }
}
