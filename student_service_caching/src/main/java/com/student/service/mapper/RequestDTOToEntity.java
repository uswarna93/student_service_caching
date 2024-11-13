package com.student.service.mapper;

import com.student.service.DTO.StudentRequestDTO;
import com.student.service.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RequestDTOToEntity {

    private static final Logger logger= LoggerFactory.getLogger(RequestDTOToEntity.class);

    public Student mappingStudentRequestDtoToEntity(StudentRequestDTO studentRequestDTO) {
       Student studentEntity=new Student();
        try {
            logger.info("Begin mappingStudentRequestDtoToEntity()");
            studentEntity.setStudentId(studentRequestDTO.getStudentId());
            studentEntity.setFirstName(studentRequestDTO.getFirstName());
            studentEntity.setLastName(studentRequestDTO.getLastName());
            studentEntity.setDepartment(studentRequestDTO.getDepartment());
        }catch (Exception e){
            logger.error("Exception in Begin mappingStudentRequestDtoToEntity()"+ e.getMessage());
        }
        return studentEntity;
    }
}
