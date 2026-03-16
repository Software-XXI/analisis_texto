package java_service.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // <--- ESTO ES LO MÁS IMPORTANTE
public class AnalysisResponseDTO {
    private String id;
    private String sentiment;
    private double score;
    // Jackson ignorará automáticamente 'analysis' y 'status' gracias a la anotación
}
