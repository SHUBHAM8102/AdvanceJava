package pom.capgemini.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response DTO containing shortened URL details")
public class UrlResponseDto {

    @Schema(description = "The generated or custom short code", example = "aBcD1234")
    private String shortCode;

    @Schema(description = "The full short URL", example = "http://localhost:8080/api/aBcD1234")
    private String shortUrl;

    @Schema(description = "The original long URL", example = "https://www.example.com/very/long/path/to/resource")
    private String originalUrl;

    @Schema(description = "Number of times the short URL has been accessed", example = "42")
    private Long clickCount;

    @Schema(description = "Timestamp when the short URL was created", example = "2025-06-15T10:32:47.123456")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when the record was last updated", example = "2025-06-15T12:00:00.000000")
    private LocalDateTime updatedAt;
}

