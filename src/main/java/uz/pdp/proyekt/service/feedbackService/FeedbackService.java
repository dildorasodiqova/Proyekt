package uz.pdp.proyekt.service.feedbackService;

import org.apache.coyote.BadRequestException;
import uz.pdp.proyekt.dtos.createDtos.FeedBackCreateDTO;
import uz.pdp.proyekt.dtos.responseDto.FeedbackResponseDTO;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {
    FeedbackResponseDTO create(FeedBackCreateDTO dto);
    FeedbackResponseDTO findById(UUID feedbackId);
    List<FeedbackResponseDTO> getByProductId(UUID productId);
    String delete(UUID feedbackId, UUID userId);
}
