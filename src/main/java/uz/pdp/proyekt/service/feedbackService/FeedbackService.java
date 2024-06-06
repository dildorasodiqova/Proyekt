package uz.pdp.proyekt.service.feedbackService;

import org.apache.coyote.BadRequestException;
import uz.pdp.proyekt.dtos.createDtos.FeedBackCreateDTO;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.FeedbackResponseDTO;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {
    BaseResponse<FeedbackResponseDTO> create(FeedBackCreateDTO dto);
    BaseResponse<FeedbackResponseDTO> findById(UUID feedbackId);
    BaseResponse<List<FeedbackResponseDTO>> getByProductId(UUID productId);
    BaseResponse<String> delete(UUID feedbackId, UUID userId);
}
