package uz.pdp.proyekt.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.proyekt.dtos.createDtos.FeedBackCreateDTO;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
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

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @PostMapping()
    public ResponseEntity<BaseResponse<FeedbackResponseDTO>> create(@Valid @RequestBody FeedBackCreateDTO createDTO) {
        return ResponseEntity.ok(feedbackService.create(createDTO));
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/getById/{feedbackId}")
    public ResponseEntity<BaseResponse<FeedbackResponseDTO>> getById(@PathVariable UUID feedbackId){
        return ResponseEntity.ok(feedbackService.findById(feedbackId));
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @DeleteMapping("/delete/{feedbackId}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable UUID feedbackId, Principal principal) {
        return ResponseEntity.ok(feedbackService.delete(feedbackId, UUID.fromString(principal.getName())));
    }

    @Operation(
            description = "This method return all feedbacks of firstly product",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/feedbackOfProduct/{productId}")
    public ResponseEntity<BaseResponse<List<FeedbackResponseDTO>>> feedbackOfProduct(@PathVariable UUID productId){
        return ResponseEntity.ok(feedbackService.getByProductId(productId));
    }
}
