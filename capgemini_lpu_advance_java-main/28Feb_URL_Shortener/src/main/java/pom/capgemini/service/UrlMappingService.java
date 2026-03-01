package pom.capgemini.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pom.capgemini.dto.StatsDto;
import pom.capgemini.dto.UrlRequestDto;
import pom.capgemini.dto.UrlResponseDto;
import pom.capgemini.entity.UrlMapping;
import pom.capgemini.exception.ShortCodeAlreadyExistsException;
import pom.capgemini.exception.ShortCodeNotFoundException;
import pom.capgemini.repository.UrlMappingRepository;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UrlMappingService {

    private static final String ALPHANUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int MAX_RETRIES = 10;
    private static final String BASE_URL = "http://localhost:8088/api/";

    private final UrlMappingRepository urlMappingRepository;

    public UrlMappingService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    @Transactional
    public UrlResponseDto createShortUrl(UrlRequestDto request) {
        String shortCode;

        if (request.getCustomAlias() != null && !request.getCustomAlias().isBlank()) {
            shortCode = request.getCustomAlias();
            if (urlMappingRepository.existsByShortCode(shortCode)) {
                throw new ShortCodeAlreadyExistsException(shortCode);
            }
        } else {
            shortCode = generateUniqueShortCode();
        }

        UrlMapping urlMapping = UrlMapping.builder()
                .originalUrl(request.getOriginalUrl())
                .shortCode(shortCode)
                .clickCount(0L)
                .build();

        UrlMapping saved = urlMappingRepository.save(urlMapping);
        return mapToResponseDto(saved);
    }

    @Transactional
    public String resolveAndTrackClick(String shortCode) {
        UrlMapping urlMapping = urlMappingRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ShortCodeNotFoundException(shortCode));

        urlMapping.setClickCount(urlMapping.getClickCount() + 1);
        urlMappingRepository.save(urlMapping);

        return urlMapping.getOriginalUrl();
    }

    public StatsDto getStats(String shortCode) {
        UrlMapping urlMapping = urlMappingRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ShortCodeNotFoundException(shortCode));

        return StatsDto.builder()
                .shortCode(urlMapping.getShortCode())
                .originalUrl(urlMapping.getOriginalUrl())
                .clickCount(urlMapping.getClickCount())
                .createdAt(urlMapping.getCreatedAt())
                .lastAccessedAt(urlMapping.getUpdatedAt())
                .build();
    }

    public Page<UrlResponseDto> getAllUrls(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return urlMappingRepository.findAll(pageable).map(this::mapToResponseDto);
    }

    @Transactional
    public void deleteByShortCode(String shortCode) {
        UrlMapping urlMapping = urlMappingRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ShortCodeNotFoundException(shortCode));
        urlMappingRepository.delete(urlMapping);
    }

    public List<UrlResponseDto> getTopUrls() {
        return urlMappingRepository.findTop5MostClickedUrls()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    private String generateUniqueShortCode() {
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            int length = 6 + RANDOM.nextInt(3);
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                sb.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
            }
            String code = sb.toString();
            if (!urlMappingRepository.existsByShortCode(code)) {
                return code;
            }
        }
        throw new RuntimeException("Failed to generate a unique short code after " + MAX_RETRIES + " attempts");
    }

    private UrlResponseDto mapToResponseDto(UrlMapping entity) {
        return UrlResponseDto.builder()
                .shortCode(entity.getShortCode())
                .shortUrl(BASE_URL + entity.getShortCode())
                .originalUrl(entity.getOriginalUrl())
                .clickCount(entity.getClickCount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
