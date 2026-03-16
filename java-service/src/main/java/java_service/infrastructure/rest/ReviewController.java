package java_service.infrastructure.rest;

import java_service.application.CreateReviewUseCase;
import java_service.application.GetReviewsUseCase;
import java_service.domain.model.Review;
import java_service.infrastructure.rest.dto.ReviewRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java_service.application.CreateReviewUseCase;
import java_service.infrastructure.rest.dto.ReviewRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final CreateReviewUseCase createReviewUseCase;
    private final GetReviewsUseCase getReviewsUseCase;

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody ReviewRequestDTO request) {
        log.info("Recibida petición de análisis para usuario: {}", request.getUserId());

        // Ejecutamos el caso de uso
        String reviewId = createReviewUseCase.execute(request.getText(), request.getUserId());

        // Respondemos con un JSON claro para el frontend
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "id", reviewId,
                "message", "Review recibida exitosamente. El análisis de sentimiento está en proceso.",
                "status", "PENDING"
        ));
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAll() {
        return ResponseEntity.ok(getReviewsUseCase.execute());
    }
}


