package java_service.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequestDTO {
    @NotBlank(message = "El texto de la review no puede estar vacío")
    private String text;

    @NotNull(message = "El ID de usuario es obligatorio")
    private Long userId;
}
