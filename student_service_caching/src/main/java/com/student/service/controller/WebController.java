package com.student.service.controller;

import com.student.service.DTO.StudentRequestDTO;
import com.student.service.DTO.StudentResponseDTO;
import com.student.service.exception.NoSuchStudentExists;
import com.student.service.exception.StudentExistsInDataBase;
import com.student.service.service.StudentService;
import com.student.service.service.StudentServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class WebController {

    private final StudentService studentService;

    private static final Logger logger= LoggerFactory.getLogger(WebController.class);

   public WebController(StudentService studentService){
       this.studentService=studentService;
   }
    @GetMapping("/getStudent")
    public ResponseEntity<?> getStudent() {
        List<StudentResponseDTO> studentResponseDTO;
        try {
            logger.info("Begin getStudent()");
            studentResponseDTO =studentService.getStudent();
        } catch (Exception e) {
            logger.error("Exception in getStudent()");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentResponseDTO);
    }
    @GetMapping("/getStudent/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable String studentId) {
        StudentResponseDTO studentResponseDTO;
        try {
            logger.info("Begin getStudentById()");
            studentResponseDTO= studentService.getStudentById(studentId);
        } catch (Exception e) {
            logger.error("Exception in getStudentById()");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentResponseDTO);
    }
    @PostMapping("/addStudent")
    public ResponseEntity<?> addStudent(@Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        try {
            logger.info("Begin addStudent()");
            studentService.addStudent(studentRequestDTO);
        } catch (Exception | StudentExistsInDataBase e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Student saved to database: "+studentRequestDTO);
    }
    @PutMapping("/updateStudent")
    public ResponseEntity<?> updateStudent(@Valid @RequestBody StudentRequestDTO studentRequestDTO) {
        try {
            logger.info("Begin updateStudent()");
            studentService.updateStudent(studentRequestDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchStudentExists e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Student updated to database");
    }
    @DeleteMapping("/deleteStudentById/{studentId}")
    public ResponseEntity<?> deleteStudentById(@Valid @PathVariable String studentId) {
        try {
            logger.info("Begin deleteStudentById()");
            studentService.deleteStudentById(studentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchStudentExists e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Student deleted from database "+studentId);
    }

}
