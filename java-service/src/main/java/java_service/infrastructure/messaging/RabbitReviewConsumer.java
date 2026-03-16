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
    public void receiveAnalysis(String messageJson) {
        try {
            AnalysisResponseDTO response = objectMapper.readValue(messageJson, AnalysisResponseDTO.class);

            // Ejecución del caso de uso
            updateReviewUseCase.execute(
                    response.getId(),
                    response.getSentiment(),
                    response.getScore()
            );

        } catch (JsonProcessingException e) {
            log.error("Error de formato JSON: El contrato con Python se ha roto. Payload: {}", messageJson);
            // No re-encolamos (NACK) porque si el JSON está mal, fallará siempre.
        } catch (Exception e) {
            log.error("Error técnico al procesar la respuesta de la IA para ID: {}", messageJson, e);
            // Opcional: lanzar excepción para que RabbitMQ re-intente (si es un error de DB temporal)
            throw e;
        }
    }
}
