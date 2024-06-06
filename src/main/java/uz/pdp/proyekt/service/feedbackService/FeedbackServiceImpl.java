package uz.pdp.proyekt.service.feedbackService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.dtos.createDtos.FeedBackCreateDTO;
import uz.pdp.proyekt.dtos.responseDto.BaseResponse;
import uz.pdp.proyekt.dtos.responseDto.FeedbackResponseDTO;
import uz.pdp.proyekt.entities.FeedbackEntity;
import uz.pdp.proyekt.entities.ProductEntity;
import uz.pdp.proyekt.entities.UserEntity;
import uz.pdp.proyekt.exception.BadRequestException;
import uz.pdp.proyekt.exception.DataNotFoundException;
import uz.pdp.proyekt.repositories.FeedBackRepository;
import uz.pdp.proyekt.service.productService.ProductService;
import uz.pdp.proyekt.service.userService.UserService;

import java.util.*;
import java.util.stream.Collectors;

import static uz.pdp.proyekt.enums.UserRole.ADMIN;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedBackRepository feedBackRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public BaseResponse<FeedbackResponseDTO> create(FeedBackCreateDTO dto) {
        ProductEntity product = productService.findById(dto.getProductId());
        UserEntity user = userService.findById(dto.getUserId());
        FeedbackEntity entity = feedBackRepository.save(new FeedbackEntity(product, user, dto.getRate(), dto.getText()));
        return BaseResponse.<FeedbackResponseDTO>builder()
                .data(parse(entity))
                .build();
    }

    private FeedbackResponseDTO parse(FeedbackEntity dto) {
        return new FeedbackResponseDTO(dto.getId(), dto.getProduct().getId(), dto.getUser().getUsername(), dto.getRate(), dto.getText(), dto.getCreatedDate(), dto.getUpdateDate());
    }

    @Override
    public BaseResponse<FeedbackResponseDTO> findById(UUID feedbackId) {
        FeedbackEntity feedback = feedBackRepository.findById(feedbackId).orElseThrow(() -> new DataNotFoundException("Feedback not found"));
        return BaseResponse.<FeedbackResponseDTO>builder()
                .data(parse(feedback))
                .success(true)
                .message("success")
                .code(200)
                .build();

    }

    @Override
    public BaseResponse<List<FeedbackResponseDTO>> getByProductId(UUID productId) {
        List<FeedbackEntity> allByProductId = feedBackRepository.findAllByProductId(productId);
        List<FeedbackResponseDTO> collect = allByProductId.stream()
                .map(this::parse)
                .toList();

        return BaseResponse.<List<FeedbackResponseDTO>>builder()
                .data(collect)
                .success(true)
                .message("success")
                .code(200)
                .build();

    }


    @Override
    public BaseResponse<String> delete(UUID feedbackId, UUID userId)  {
        FeedbackEntity feedback = feedBackRepository.findById(feedbackId).orElseThrow(() -> new DataNotFoundException("Feedback not found !"));
        UserEntity user = userService.findById(userId);
        if (!(Objects.equals(feedback.getUser().getId(), userId) || Objects.equals(user.getUserRole(), ADMIN))){
           throw new BadRequestException("Something is wrong ! \n Please try again !");
        }
        feedBackRepository.delete(feedback);
        return BaseResponse.<String>builder()
                .data("Successfully")
                .success(true)
                .message("success")
                .code(200)
                .build();
    }
}
