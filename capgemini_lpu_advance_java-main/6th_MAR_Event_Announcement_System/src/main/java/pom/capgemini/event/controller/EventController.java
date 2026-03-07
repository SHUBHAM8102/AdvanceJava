package pom.capgemini.event.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pom.capgemini.event.dto.EventRequest;
import pom.capgemini.event.dto.EventResponse;
import pom.capgemini.event.model.EventType;
import pom.capgemini.event.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventResponse> getAllEvents() {
        return eventService.getAllEvents().stream().map(EventResponse::fromEntity).toList();
    }

    @GetMapping("/upcoming")
    public List<EventResponse> getUpcomingEvents() {
        return eventService.getUpcomingEvents().stream().map(EventResponse::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public EventResponse getEventById(@PathVariable Long id) {
        return EventResponse.fromEntity(eventService.getEventById(id));
    }

    @GetMapping("/type/{eventType}")
    public List<EventResponse> getEventsByType(@PathVariable EventType eventType) {
        return eventService.getEventsByType(eventType).stream().map(EventResponse::fromEntity).toList();
    }

    @GetMapping("/search")
    public List<EventResponse> searchByTitle(@RequestParam String title) {
        return eventService.searchByTitle(title).stream().map(EventResponse::fromEntity).toList();
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest request) {
        EventResponse response = EventResponse.fromEntity(eventService.createEvent(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public EventResponse updateEvent(@PathVariable Long id, @Valid @RequestBody EventRequest request) {
        return EventResponse.fromEntity(eventService.updateEvent(id, request));
    }

    @PatchMapping("/{id}/cancel")
    public EventResponse cancelEvent(@PathVariable Long id) {
        return EventResponse.fromEntity(eventService.cancelEvent(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}

