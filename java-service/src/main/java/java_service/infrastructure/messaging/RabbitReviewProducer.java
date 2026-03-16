package java_service.infrastructure.messaging;

import java_service.domain.model.Review;
import java_service.domain.model.ports.ReviewNotificationPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitReviewProducer implements ReviewNotificationPort {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.request-queue:analysis_request}")
    private String queueName;

    @Override
    public void notifyForAnalysis(Review review) {
        log.info("Publicando review {} en la cola: {}", review.getId(), queueName);

        // Creamos el mensaje que Python espera recibir
        Map<String, Object> message = Map.of(
                "id", review.getId(),
                "text", review.getContent()
        );

        rabbitTemplate.convertAndSend(queueName, message);
    }
}
