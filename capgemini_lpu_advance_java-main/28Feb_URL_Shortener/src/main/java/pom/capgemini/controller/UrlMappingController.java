package pom.capgemini.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pom.capgemini.dto.*;
import pom.capgemini.service.UrlMappingService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "URL Shortener", description = "REST API for URL Shortener Service")
public class UrlMappingController {

    private final UrlMappingService urlMappingService;

    public UrlMappingController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @Operation(summary = "Create a short URL", description = "Accepts a long URL and an optional custom alias, returns the shortened URL details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Short URL created successfully",
                    content = @Content(schema = @Schema(implementation = UrlResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed — invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Custom alias already exists",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/shorten")
    public ResponseEntity<UrlResponseDto> createShortUrl(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body containing the original URL and optional custom alias",
                    required = true)
            @Valid @RequestBody UrlRequestDto request) {
        UrlResponseDto response = urlMappingService.createShortUrl(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Redirect to original URL", description = "Resolves the short code and redirects (HTTP 302) to the original URL. Increments click count.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirect to original URL"),
            @ApiResponse(responseCode = "404", description = "Short code not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginalUrl(
            @Parameter(description = "The short code to resolve", required = true, example = "aBcD1234")
            @PathVariable String shortCode) {
        String originalUrl = urlMappingService.resolveAndTrackClick(shortCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

    @Operation(summary = "Get URL statistics", description = "Returns click count, timestamps, and other statistics for the given short code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully",
                    content = @Content(schema = @Schema(implementation = StatsDto.class))),
            @ApiResponse(responseCode = "404", description = "Short code not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/stats/{shortCode}")
    public ResponseEntity<StatsDto> getStats(
            @Parameter(description = "The short code to retrieve statistics for", required = true, example = "aBcD1234")
            @PathVariable String shortCode) {
        StatsDto stats = urlMappingService.getStats(shortCode);
        return ResponseEntity.ok(stats);
    }

    @Operation(summary = "List all URLs (paginated)", description = "Returns a paginated list of all stored URL mappings.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paginated list of URL mappings")
    })
    @GetMapping("/urls")
    public ResponseEntity<Page<UrlResponseDto>> getAllUrls(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        Page<UrlResponseDto> urls = urlMappingService.getAllUrls(page, size);
        return ResponseEntity.ok(urls);
    }

    @Operation(summary = "Delete a short URL", description = "Deletes the URL mapping for the given short code. Returns 204 on success.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Short URL deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Short code not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> deleteShortUrl(
            @Parameter(description = "The short code to delete", required = true, example = "aBcD1234")
            @PathVariable String shortCode) {
        urlMappingService.deleteByShortCode(shortCode);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get top 5 most-clicked URLs", description = "Returns the top 5 URLs ordered by click count descending. Returns an empty list if no entries exist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Top 5 most-clicked URLs retrieved successfully")
    })
    @GetMapping("/top")
    public ResponseEntity<List<UrlResponseDto>> getTopUrls() {
        List<UrlResponseDto> topUrls = urlMappingService.getTopUrls();
        return ResponseEntity.ok(topUrls);
    }
}
