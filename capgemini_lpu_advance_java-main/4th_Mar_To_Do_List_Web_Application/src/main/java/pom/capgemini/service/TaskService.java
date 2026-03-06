package pom.capgemini.service;

import org.springframework.web.multipart.MultipartFile;
import pom.capgemini.model.Task;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> getAllTasks();

    void saveTask(Task task, MultipartFile imageFile) throws IOException;

    void toggleStatus(Long id);

    void deleteTask(Long id);

    Optional<Task> getTaskById(Long id);
}

