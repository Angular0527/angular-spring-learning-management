package ca.utoronto.lms.faculty.model;

import ca.utoronto.lms.shared.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Teacher extends BaseEntity<Long> {
    private Long userId;

    @OneToMany(mappedBy = "dean")
    private Set<Faculty> faculties = new HashSet<>();

    @OneToMany(mappedBy = "manager")
    private Set<StudyProgram> studyPrograms = new HashSet<>();

    @OneToMany(mappedBy = "mentor")
    private Set<Thesis> theses = new HashSet<>();
}
