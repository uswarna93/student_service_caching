package com.student.service.service;

import com.student.service.DTO.StudentRequestDTO;
import com.student.service.DTO.StudentResponseDTO;
import com.student.service.entity.Student;
import com.student.service.exception.NoSuchStudentExists;
import com.student.service.exception.StudentExistsInDataBase;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface StudentService {
    public StudentResponseDTO getStudentById(String studentId);
    public Student getStudent(String studentId);
    public List<StudentResponseDTO> getStudent();
    public void addStudent(StudentRequestDTO studentRequestDTO) throws StudentExistsInDataBase;
    public void updateStudent(StudentRequestDTO studentRequestDTO) throws NoSuchStudentExists;
    public void deleteStudentById(String studentId) throws NoSuchStudentExists;
}
