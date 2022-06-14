package ca.utoronto.lms.faculty.dto;

import ca.utoronto.lms.shared.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThesisDTO extends BaseDTO<Long> {
    private String name;
    private String description;
    private String url;
    private LocalDate applicationDate;
    private LocalDate defenseDate;
    private Integer grade;
    private StudentDTO student;
    private TeacherDTO mentor;
}
