package com.student.service.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class StudentRequestDTO {

    private String studentId;

    @NotNull(message = "firstName cannot be null")
    private String firstName;
    private  String lastName;

    @NotNull(message = "department cannot be null")
    private String department;
}
