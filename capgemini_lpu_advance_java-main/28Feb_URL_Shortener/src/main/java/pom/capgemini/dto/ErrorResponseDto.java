package pom.capgemini.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Generic error response DTO for 4xx/5xx responses")
public class ErrorResponseDto {

    @Schema(description = "Timestamp of the error", example = "2025-06-15T10:32:47.123456")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "Human-readable error message", example = "Short code 'abc123' not found")
    private String message;

    @Schema(description = "Field-level validation errors (if applicable)")
    private Map<String, String> fieldErrors;


}

