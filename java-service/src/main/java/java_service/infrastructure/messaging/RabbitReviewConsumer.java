package java_service.infrastructure.messaging;


import java_service.application.UpdateReviewUseCase;
import java_service.infrastructure.messaging.dto.AnalysisResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitReviewConsumer {

    private final UpdateReviewUseCase updateReviewUseCase;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "analysis_response")
    public void receiveAnalysis(AnalysisResponseDTO response) {
        try {
            log.info("📩 Recibido objeto desde Python: {}", response.getId());

            // Ejecución del caso de uso
            updateReviewUseCase.execute(
                    response.getId(),
                    response.getSentiment(),
                    response.getScore()
            );
            log.info("✅ Review {} actualizada con éxito", response.getId());

        } catch (Exception e) {
            log.error("Error de formato JSON: El contrato con Python se ha roto. Payload: {}", e.getMessage());
            // No re-encolamos (NACK) porque si el JSON está mal, fallará siempre.
        }
    }
}
