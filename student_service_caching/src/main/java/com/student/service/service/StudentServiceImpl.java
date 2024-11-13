package com.student.service.service;

import com.student.service.DTO.StudentRequestDTO;
import com.student.service.DTO.StudentResponseDTO;
import com.student.service.entity.Student;
import com.student.service.exception.NoSuchStudentExists;
import com.student.service.exception.StudentExistsInDataBase;
import com.student.service.mapper.EntityToResponseDto;
import com.student.service.mapper.RequestDTOToEntity;
import com.student.service.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

   private static final Logger logger= LoggerFactory.getLogger(StudentServiceImpl.class);

   private final StudentRepository studentRepository;
   private final EntityToResponseDto entityToResponseDto;
   private final RequestDTOToEntity requestDTOToEntity;


    public StudentServiceImpl(StudentRepository studentRepository,
                              EntityToResponseDto entityToResponseDto,
                              RequestDTOToEntity requestDTOToEntity) {
        this.studentRepository = studentRepository;
        this.entityToResponseDto = entityToResponseDto;
        this.requestDTOToEntity = requestDTOToEntity;
    }


    @Cacheable(value = "Student", key = "#studentId")
    public StudentResponseDTO getStudentById(String studentId) {
        Student studentEntity;
        try {
            logger.info("Begin getStudentById()");

            studentEntity = getStudent(studentId);
            StudentResponseDTO studentResponseDTO = null;
            if (!studentEntity.getStudentId().isEmpty()) {
                studentResponseDTO = entityToResponseDto.mappingEntityToResponseDTo(studentEntity);
            }else {
                throw new RuntimeException
                        ("There is no student present with student id "+studentId);
            }
            return studentResponseDTO;
        }catch (Exception e){
            logger.error("Exception in getStudentById() "+e.getMessage());
            throw new RuntimeException("There is no student present with student id "+studentId);
        }
    }

    public Student getStudent(String studentId) {
        logger.info("Begin getStudent from DB");
        Student studentEntity;
        studentEntity = studentRepository.getReferenceById(studentId);
        return studentEntity;
    }

    @Cacheable(value = "Student")
    public List<StudentResponseDTO> getStudent() {
        List<StudentResponseDTO> studentResponseDTOList =new ArrayList<>();
        List<Student> studentEntities;
        try{
            logger.info("Begin getStudent() ");
            studentEntities =studentRepository.findAll();
            for (Student studentEntity: studentEntities){
                StudentResponseDTO studentResponse = entityToResponseDto.mappingEntityToResponseDTo(studentEntity);
                studentResponseDTOList.add(studentResponse);
            }
            return studentResponseDTOList;
        }catch (Exception e){
            logger.error("Exception in getStudent() "+e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

   @CachePut(value = "Student",key = "#studentRequestDTO.studentId")
    public void addStudent(StudentRequestDTO studentRequestDTO) throws StudentExistsInDataBase {
        Student studentEntity;
        try {
            logger.info("Begin saveStudent() ");
            if(!isStudentExists(studentRequestDTO)){
                studentEntity = requestDTOToEntity.mappingStudentRequestDtoToEntity(studentRequestDTO);
                studentRepository.save(studentEntity);
            }else {
                throw new StudentExistsInDataBase("Student already present in database please enter different student Id");
            }
        }catch (StudentExistsInDataBase e) {
            throw new StudentExistsInDataBase(e.getMessage());
        }
    }
    private boolean isStudentExists(StudentRequestDTO studentRequestDTO ) {
        return studentRepository.existsById(studentRequestDTO.getStudentId());
    }

   @CachePut(value = "Student", key = "#studentRequestDTO")
    public void updateStudent(StudentRequestDTO studentRequestDTO)  {
        Student studentEntity;
        try {
                logger.info("Begin updateStudent() ");
                studentEntity = requestDTOToEntity.mappingStudentRequestDtoToEntity(studentRequestDTO);
                studentRepository.save(studentEntity);
        }catch (Exception e){
            logger.error("Exception in updateStudent() "+e.getMessage());
        }
    }
    @CacheEvict(value = "Student", key = "#studentId")
    public void deleteStudentById(String studentId) throws NoSuchStudentExists {
        try {
            logger.info("Begin deleteStudentById() ");
            if(studentRepository.existsById(studentId)) {
                studentRepository.deleteById(studentId);
            }else {
                throw new NoSuchStudentExists("No student exists in database with studentId "+studentId);
            }
        }catch (Exception e){
            logger.error("Exception in deleteStudentById() "+e.getMessage());
        } catch (NoSuchStudentExists e) {
            throw new NoSuchStudentExists(e.getMessage());
        }
    }
}
