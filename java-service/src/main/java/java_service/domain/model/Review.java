package java_service.domain.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Review {
    private final String id;
    private final String content;
    private final Long userId;
    private final String status;
    private final String sentiment;
    private final Double confidenceScore;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    // Lógica de negocio pura: El dominio decide cómo mutar,
    // pero como es inmutable (final), devolvemos una nueva instancia
    public Review completeAnalysis(String sentiment, Double score) {
        return this.toBuilder()
                .sentiment(sentiment != null ? sentiment.toUpperCase() : "UNKNOWN")
                .confidenceScore(score)
                .status("COMPLETED")
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
