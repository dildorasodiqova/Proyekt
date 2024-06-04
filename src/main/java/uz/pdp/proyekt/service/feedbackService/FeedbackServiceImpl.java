package uz.pdp.proyekt.service.feedbackService;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import uz.pdp.proyekt.dtos.createDtos.FeedBackCreateDTO;
import uz.pdp.proyekt.dtos.responseDto.FeedbackResponseDTO;
import uz.pdp.proyekt.entities.FeedbackEntity;
import uz.pdp.proyekt.entities.ProductEntity;
import uz.pdp.proyekt.entities.UserEntity;
import uz.pdp.proyekt.exception.DataNotFoundException;
import uz.pdp.proyekt.repositories.FeedBackRepository;
import uz.pdp.proyekt.repositories.ProductRepository;
import uz.pdp.proyekt.repositories.UserRepository;
import uz.pdp.proyekt.service.productService.ProductService;
import uz.pdp.proyekt.service.userService.UserService;

import java.util.*;

import static uz.pdp.proyekt.enums.UserRole.ADMIN;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedBackRepository feedBackRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public FeedbackResponseDTO create(FeedBackCreateDTO dto) {
        ProductEntity product = productService.findById(dto.getProductId());
        UserEntity user = userService.findById(dto.getUserId());

        return parse(feedBackRepository.save(new FeedbackEntity(product, user, dto.getRate(), dto.getText())));
    }

    private FeedbackResponseDTO parse(FeedbackEntity dto) {
        return new FeedbackResponseDTO(dto.getProduct().getId(), dto.getUser().getUsername(), dto.getRate(), dto.getText());
    }

    @Override
    public FeedbackResponseDTO findById(UUID feedbackId) {
        FeedbackEntity feedback = feedBackRepository.findById(feedbackId).orElseThrow(() -> new DataNotFoundException("Feedback not found"));
        return parse(feedback);
    }

    @Override
    public List<FeedbackResponseDTO> getByProductId(UUID productId) {
        List<FeedbackEntity> allByProductId = feedBackRepository.findAllByProductId(productId);
        List<FeedbackResponseDTO> list = new ArrayList<>();
        for (FeedbackEntity feedback : allByProductId) {
            FeedbackResponseDTO parse = parse(feedback);
            list.add(parse);
        }
        return list;
    }

    @Override
    public String delete(UUID feedbackId, UUID userId) throws BadRequestException {
        FeedbackEntity feedback = feedBackRepository.findById(feedbackId).orElseThrow(() -> new DataNotFoundException("Feedback not found !"));
        UserEntity user = userService.findById(userId);
        if (!(Objects.equals(feedback.getUser().getId(), userId) || Objects.equals(user.getUserRole(), ADMIN))){
           throw new BadRequestException("Something is wrong ! \n Please try again !");
        }
        feedBackRepository.delete(feedback);
        return "Successfully";
    }
}