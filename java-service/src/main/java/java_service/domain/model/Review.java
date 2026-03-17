package java_service.domain.model;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value // Esto la hace inmutable (campos final)
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Review {
    String id;
    Long userId;
    String content;
    String sentiment;
    Double confidenceScore;
    String status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    // Tu método actual está BIEN, el secreto es USAR su retorno
    public Review completeAnalysis(String sentiment, Double score) {
        return this.toBuilder()
                .sentiment(sentiment)
                .confidenceScore(score)
                .status("COMPLETED")
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
