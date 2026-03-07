package pom.capgemini.event.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pom.capgemini.common.ResourceNotFoundException;
import pom.capgemini.event.dto.EventRequest;
import pom.capgemini.event.entity.Event;
import pom.capgemini.event.model.EventStatus;
import pom.capgemini.event.model.EventType;
import pom.capgemini.event.repository.EventRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        Sort sort = Sort.by(Sort.Order.asc("eventDate"), Sort.Order.asc("startTime"));
        return eventRepository.findAll(sort);
    }

    public List<Event> getUpcomingEvents() {
        return eventRepository.findByStatusAndEventDateGreaterThanEqualOrderByEventDateAscStartTimeAsc(
                EventStatus.SCHEDULED,
                LocalDate.now()
        );
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
    }

    public List<Event> getEventsByType(EventType eventType) {
        return eventRepository.findByEventTypeOrderByEventDateAscStartTimeAsc(eventType);
    }

    public List<Event> searchByTitle(String title) {
        return eventRepository.findByTitleContainingIgnoreCaseOrderByEventDateAscStartTimeAsc(title);
    }

    public Event createEvent(EventRequest request) {
        Event event = new Event();
        mapRequestToEntity(request, event);
        if (event.getStatus() == null) {
            event.setStatus(EventStatus.SCHEDULED);
        }
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, EventRequest request) {
        Event event = getEventById(id);
        mapRequestToEntity(request, event);
        if (event.getStatus() == null) {
            event.setStatus(EventStatus.SCHEDULED);
        }
        return eventRepository.save(event);
    }

    public Event cancelEvent(Long id) {
        Event event = getEventById(id);
        event.setStatus(EventStatus.CANCELLED);
        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        Event event = getEventById(id);
        eventRepository.delete(event);
    }

    private void mapRequestToEntity(EventRequest request, Event event) {
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventType(request.getEventType());
        event.setLocation(request.getLocation());
        event.setEventDate(request.getEventDate());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setOrganizerName(request.getOrganizerName());
        event.setContactPhone(request.getContactPhone());
        event.setStatus(request.getStatus());
    }
}

