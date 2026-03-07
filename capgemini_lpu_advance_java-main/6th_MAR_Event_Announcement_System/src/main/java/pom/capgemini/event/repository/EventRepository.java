package pom.capgemini.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pom.capgemini.event.entity.Event;
import pom.capgemini.event.model.EventStatus;
import pom.capgemini.event.model.EventType;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByStatusAndEventDateGreaterThanEqualOrderByEventDateAscStartTimeAsc(EventStatus status, LocalDate eventDate);

    List<Event> findByEventTypeOrderByEventDateAscStartTimeAsc(EventType eventType);

    List<Event> findByTitleContainingIgnoreCaseOrderByEventDateAscStartTimeAsc(String title);
}

