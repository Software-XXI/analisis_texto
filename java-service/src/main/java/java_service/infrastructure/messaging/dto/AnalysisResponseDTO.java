package java_service.infrastructure.messaging.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnalysisResponseDTO {
    private String id;
    private String sentiment;
    private double score;
}
