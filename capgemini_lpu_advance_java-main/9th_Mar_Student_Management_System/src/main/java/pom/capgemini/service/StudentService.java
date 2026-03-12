package pom.capgemini.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pom.capgemini.dto.StudentDTO;
import pom.capgemini.entity.Student;

public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);
    StudentDTO getStudentById(Long id);
    Page<StudentDTO> getAllStudents(Pageable pageable);
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);
    void deleteStudent(Long id);
    String uploadProfileImage(Long id, byte[] imageData);
    String uploadAssignmentFile(Long id, byte[] fileData);
    byte[] downloadProfileImage(Long id);
    byte[] downloadAssignmentFile(Long id);
}

