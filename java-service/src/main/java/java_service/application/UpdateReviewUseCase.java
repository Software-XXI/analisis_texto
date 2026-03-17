package java_service.application;

import java_service.domain.exceptions.ReviewNotFoundException;
import java_service.domain.model.Review;
import java_service.domain.model.ports.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateReviewUseCase {

    private final ReviewRepository repository;

    @Transactional
    public void execute(String reviewId, String sentiment, Double score) {
        // 1. Obtener el estado actual (Inmutable)
        Review reviewActual = repository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));

        // 2. Crear la nueva versión del dominio (Inmutable)
        // Aquí es donde "evolucionas" el objeto sin que el modelo tenga lógica extra
        Review reviewProcesada = new Review(
                reviewActual.getId(),
                reviewActual.getUserId(),
                reviewActual.getContent(),
                sentiment, // Nuevo dato de BERT
                score,     // Nuevo dato de BERT
                "COMPLETED", // Nuevo estado
                reviewActual.getCreatedAt(),
                LocalDateTime.now() // updatedAt
        );

        // 3. Persistir la nueva versión
        repository.save(reviewProcesada);

        log.info("✅ Review {} marcada como COMPLETED en el dominio", reviewId);
    }
}
