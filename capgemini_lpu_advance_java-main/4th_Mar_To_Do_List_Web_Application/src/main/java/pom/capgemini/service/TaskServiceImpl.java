package pom.capgemini.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pom.capgemini.model.Task;
import pom.capgemini.repository.TaskRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void saveTask(Task task, MultipartFile imageFile) throws IOException {
        task.setStatus("PENDING");

        if (imageFile != null && !imageFile.isEmpty()) {
            task.setImageName(imageFile.getOriginalFilename());
            task.setImageType(imageFile.getContentType());
            task.setImageData(imageFile.getBytes());
        }

        taskRepository.save(task);
    }

    @Override
    public void toggleStatus(Long id) {
        taskRepository.findById(id).ifPresent(task -> {
            if ("PENDING".equals(task.getStatus())) {
                task.setStatus("COMPLETE");
            } else {
                task.setStatus("PENDING");
            }
            taskRepository.save(task);
        });
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
}

