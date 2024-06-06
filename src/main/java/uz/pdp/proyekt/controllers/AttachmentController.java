package uz.pdp.proyekt.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.proyekt.entities.AttachmentEntity;
import uz.pdp.proyekt.service.attachmentService.AttachmentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/multiple-upload")
    public List<UUID> multipleUpload(@RequestParam("files") MultipartFile[] files) throws IOException {
        List<UUID> fileIdList = new ArrayList<>(files.length);
        for (MultipartFile file : files) {
            UUID uuid = attachmentService.uploadImage(file);
            fileIdList.add(uuid);
        }
        return fileIdList;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/single-upload")
    public ResponseEntity<UUID> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return ResponseEntity.ok(attachmentService.uploadImage(file));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable UUID fileId) {
        AttachmentEntity attachment = attachmentService.downloadImage(fileId);

        return ResponseEntity.ok(attachment.getBytes());
    }
}