package uz.pdp.proyekt.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.proyekt.dtos.createDtos.FeedBackCreateDTO;
import uz.pdp.proyekt.dtos.responseDto.FeedbackResponseDTO;
import uz.pdp.proyekt.service.feedbackService.FeedbackService;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class  FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping()
    public ResponseEntity<FeedbackResponseDTO> create(@RequestBody FeedBackCreateDTO createDTO) {
        return ResponseEntity.ok(feedbackService.create(createDTO));
    }

    @GetMapping("/getById/{feedbackId}")
    public ResponseEntity<FeedbackResponseDTO> getById(@PathVariable UUID feedbackId){
        return ResponseEntity.ok(feedbackService.findById(feedbackId));
    }

    @DeleteMapping("/delete/{feedbackId}")
    public ResponseEntity<String> delete(@PathVariable UUID feedbackId, Principal principal) {
        return ResponseEntity.ok(feedbackService.delete(feedbackId, UUID.fromString(principal.getName())));
    }

    @GetMapping("/feedbackOfProduct/{productId}")
    public ResponseEntity<List<FeedbackResponseDTO>> feedbackOfProduct(@PathVariable UUID productId){
        return ResponseEntity.ok(feedbackService.getByProductId(productId));
    }
}
