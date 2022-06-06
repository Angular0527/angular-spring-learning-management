package ca.utoronto.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudyProgramDTO extends BaseDTO<Long> {
    private String name;
    private String description;
    private FacultyDTO faculty;
    private TeacherDTO manager;
}