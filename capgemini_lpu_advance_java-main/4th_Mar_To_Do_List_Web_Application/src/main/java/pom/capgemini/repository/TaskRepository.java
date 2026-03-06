package pom.capgemini.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pom.capgemini.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

