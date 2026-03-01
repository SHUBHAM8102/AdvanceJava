package pom.capgemini.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request DTO for creating a shortened URL")
public class UrlRequestDto {

    @NotBlank(message = "Original URL must not be blank")
    @URL(message = "Must be a valid URL")
    @Schema(description = "The original long URL to shorten", example = "https://www.example.com/very/long/path/to/resource")
    private String originalUrl;

    @Size(min = 6, max = 8, message = "Custom alias must be between 6 and 8 characters")
    @Schema(description = "Optional custom alias for the short code (6-8 alphanumeric characters)", example = "myAlias1")
    private String customAlias;
}

