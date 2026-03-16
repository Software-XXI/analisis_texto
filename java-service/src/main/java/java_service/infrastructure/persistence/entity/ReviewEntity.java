package java_service.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {
    @Id
    private String id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long userId;
    private String status;
    private String sentiment;
    private Double confidenceScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
