package com.student.service.DTO;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class StudentResponseDTO implements Serializable {

    private String studentId;
    private String firstName;
    private  String lastName;
    private String department;
}
