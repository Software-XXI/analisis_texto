package java_service.application;

import java_service.domain.exceptions.ReviewNotFoundException;
import java_service.domain.model.Review;
import java_service.domain.model.ports.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateReviewUseCase {

    private final ReviewRepository repository;

    @Transactional
    public void execute(String reviewId, String sentiment, double score) {
        log.info("Iniciando actualización de review ID: {} con sentimiento: {}", reviewId, sentiment);

        Review review = repository.findById(reviewId)
                .orElseThrow(() -> {
                    log.error("No se encontró la review con ID: {}", reviewId);
                    return new ReviewNotFoundException(reviewId);
                });

        review.completeAnalysis(sentiment, score);

        repository.save(review);
        log.info("Review {} actualizada exitosamente a estado COMPLETED", reviewId);
    }
}
