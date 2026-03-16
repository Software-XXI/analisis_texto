package java_service.infrastructure.persistence.mapper;

import java_service.domain.model.Review;
import java_service.infrastructure.persistence.entity.ReviewEntity;
import org.springframework.stereotype.Component;

@Component
public class ReviewPersistenceMapper {

    public ReviewEntity toEntity(Review domain) {
        return new ReviewEntity(
                domain.getId(), domain.getContent(), domain.getUserId(),
                domain.getStatus(), domain.getSentiment(), domain.getConfidenceScore(),
                domain.getCreatedAt(), domain.getUpdatedAt()
        );
    }

    public Review toDomain(ReviewEntity entity) {
        return Review.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .userId(entity.getUserId())
                .status(entity.getStatus())
                .sentiment(entity.getSentiment())
                .confidenceScore(entity.getConfidenceScore())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
