package java_service.application;

import java_service.domain.model.Review;
import java_service.domain.model.ports.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetReviewsUseCase {
    private final ReviewRepository repository;

    public List<Review> execute() {
        return repository.findAll();
    }
}