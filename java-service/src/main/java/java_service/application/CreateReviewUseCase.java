package java_service.application;

import java_service.domain.model.Review;
import java_service.domain.model.ports.ReviewNotificationPort;
import java_service.domain.model.ports.ReviewRepository;
import java_service.infrastructure.messaging.RabbitReviewProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateReviewUseCase {

    private final ReviewRepository repository;
    private final RabbitReviewProducer producer;
    private final ReviewNotificationPort notificationPort;

    @Transactional
    public String execute(String text, Long userId) {
        log.info("Iniciando creación de review para el usuario: {}", userId);

        // 1. Crear el objeto de Dominio (Inmutable)
        Review newReview = Review.builder()
                .id(UUID.randomUUID().toString())
                .content(text)
                .userId(userId)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        // 2. Persistir en PostgreSQL a través del puerto
        Review savedReview = repository.save(newReview);
        log.info("Review guardada en base de datos con ID: {}", savedReview.getId());

        // 3. Enviar a RabbitMQ para que Python la analice
        notificationPort.notifyForAnalysis(savedReview);

        return savedReview.getId();
    }
}
