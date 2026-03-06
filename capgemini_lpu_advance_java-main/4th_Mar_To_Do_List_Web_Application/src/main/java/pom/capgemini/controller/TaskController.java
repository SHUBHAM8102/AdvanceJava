package pom.capgemini.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pom.capgemini.model.Task;
import pom.capgemini.service.TaskService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/tasks";
    }

    @GetMapping("/tasks")
    public String listTasks(Model model) {
        List<Task> taskList = taskService.getAllTasks();
        long pendingCount = taskList.stream().filter(task -> "PENDING".equals(task.getStatus())).count();
        long completeCount = taskList.stream().filter(task -> "COMPLETE".equals(task.getStatus())).count();

        model.addAttribute("taskList", taskList);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("completeCount", completeCount);
        return "task-list";
    }

    @GetMapping("/tasks/new")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "task-form";
    }

    @PostMapping("/tasks/save")
    public String saveTask(@Valid @ModelAttribute("task") Task task,
                           BindingResult bindingResult,
                           @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "task-form";
        }

        try {
            taskService.saveTask(task, imageFile);
        } catch (IOException ex) {
            model.addAttribute("imageError", "Image upload failed. Please try again.");
            return "task-form";
        }

        return "redirect:/tasks";
    }

    @GetMapping("/tasks/toggle/{id}")
    public String toggleStatus(@PathVariable Long id) {
        taskService.toggleStatus(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks/image/{id}")
    public ResponseEntity<byte[]> getTaskImage(@PathVariable Long id) {
        Optional<Task> taskOptional = taskService.getTaskById(id);

        if (taskOptional.isEmpty() || taskOptional.get().getImageData() == null) {
            return ResponseEntity.notFound().build();
        }

        Task task = taskOptional.get();
        MediaType mediaType;

        try {
            mediaType = task.getImageType() != null
                    ? MediaType.parseMediaType(task.getImageType())
                    : MediaType.APPLICATION_OCTET_STREAM;
        } catch (IllegalArgumentException ex) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CACHE_CONTROL, "max-age=3600")
                .body(task.getImageData());
    }

    @GetMapping("/tasks/health")
    @ResponseBody
    public String health() {
        return "TaskTrack App is running on port 8078!";
    }
}
