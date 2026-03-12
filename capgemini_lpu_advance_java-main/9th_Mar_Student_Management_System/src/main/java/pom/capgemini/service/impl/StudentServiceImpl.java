package pom.capgemini.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import pom.capgemini.dto.StudentDTO;
import pom.capgemini.entity.Student;
import pom.capgemini.repository.StudentRepository;
import pom.capgemini.service.StudentService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "students", allEntries = true)
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setCourse(studentDTO.getCourse());
        student.setMarks(studentDTO.getMarks());

        Student savedStudent = studentRepository.save(student);
        return mapToDTO(savedStudent);
    }

    @Override
    @Cacheable(value = "students", key = "#id")
    @PostAuthorize("hasRole('ADMIN') or returnObject.email == authentication.name")
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return mapToDTO(student);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Page<StudentDTO> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable)
            .map(this::mapToDTO);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "students", key = "#id")
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setCourse(studentDTO.getCourse());
        student.setMarks(studentDTO.getMarks());

        Student updatedStudent = studentRepository.save(student);
        return mapToDTO(updatedStudent);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "students", key = "#id")
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        studentRepository.delete(student);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "students", key = "#id")
    public String uploadProfileImage(Long id, byte[] imageData) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        student.setProfileImage(imageData);
        studentRepository.save(student);
        return "Profile image uploaded successfully";
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(value = "students", key = "#id")
    public String uploadAssignmentFile(Long id, byte[] fileData) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        student.setAssignmentFile(fileData);
        studentRepository.save(student);
        return "Assignment file uploaded successfully";
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public byte[] downloadProfileImage(Long id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return student.getProfileImage();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public byte[] downloadAssignmentFile(Long id) {
        Student student = studentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return student.getAssignmentFile();
    }

    private StudentDTO mapToDTO(Student student) {
        return new StudentDTO(
            student.getId(),
            student.getName(),
            student.getEmail(),
            student.getCourse(),
            student.getMarks()
        );
    }
}

