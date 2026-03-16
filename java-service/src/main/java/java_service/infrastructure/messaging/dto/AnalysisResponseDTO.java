package java_service.infrastructure.messaging.dto;

import lombok.Data;

@Data
public class AnalysisResponseDTO {
    private String id;
    private String sentiment;
    private double score;
}
