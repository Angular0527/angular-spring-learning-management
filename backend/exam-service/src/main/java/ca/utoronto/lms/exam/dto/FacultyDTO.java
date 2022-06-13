package ca.utoronto.lms.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FacultyDTO extends BaseDTO<Long> {
    private String name;
    private String description;
    private String email;
}
