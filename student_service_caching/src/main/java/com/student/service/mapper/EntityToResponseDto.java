package com.student.service.mapper;

import com.student.service.DTO.StudentResponseDTO;
import com.student.service.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class EntityToResponseDto {

    static final Logger logger= LoggerFactory.getLogger(EntityToResponseDto.class);
    public StudentResponseDTO mappingEntityToResponseDTo(Student studentEntity) {
    logger.info("Begin mappingEntityToResponseDTo()");
     StudentResponseDTO studentResponseDTO=new StudentResponseDTO();
     studentResponseDTO.setStudentId(studentEntity.getStudentId());
     studentResponseDTO.setFirstName(studentEntity.getFirstName());
     studentResponseDTO.setLastName(studentEntity.getLastName());
     studentResponseDTO.setDepartment(studentEntity.getDepartment());

     return studentResponseDTO;
    }
}
