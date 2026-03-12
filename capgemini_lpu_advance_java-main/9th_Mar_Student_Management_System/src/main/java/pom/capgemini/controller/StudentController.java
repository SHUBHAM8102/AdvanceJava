package pom.capgemini.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pom.capgemini.dto.StudentDTO;
import pom.capgemini.service.StudentService;

import java.io.IOException;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<Page<StudentDTO>> getAllStudents(Pageable pageable) {
        Page<StudentDTO> students = studentService.getAllStudents(pageable);
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

    @PostMapping("/{id}/profile-image")
    public ResponseEntity<String> uploadProfileImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        byte[] imageData = file.getBytes();
        String message = studentService.uploadProfileImage(id, imageData);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{id}/assignment-file")
    public ResponseEntity<String> uploadAssignmentFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        byte[] fileData = file.getBytes();
        String message = studentService.uploadAssignmentFile(id, fileData);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{id}/profile-image")
    public ResponseEntity<byte[]> downloadProfileImage(@PathVariable Long id) {
        byte[] imageData = studentService.downloadProfileImage(id);
        if (imageData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"profile_image\"")
            .body(imageData);
    }

    @GetMapping("/{id}/assignment-file")
    public ResponseEntity<byte[]> downloadAssignmentFile(@PathVariable Long id) {
        byte[] fileData = studentService.downloadAssignmentFile(id);
        if (fileData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"assignment_file\"")
            .body(fileData);
    }
}

