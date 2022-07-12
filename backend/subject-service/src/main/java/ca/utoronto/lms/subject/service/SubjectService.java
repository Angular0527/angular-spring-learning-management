package ca.utoronto.lms.subject.service;

import ca.utoronto.lms.shared.security.SecurityUtils;
import ca.utoronto.lms.shared.service.ExtendedService;
import ca.utoronto.lms.subject.dto.SubjectDTO;
import ca.utoronto.lms.subject.feign.FacultyFeignClient;
import ca.utoronto.lms.subject.mapper.SubjectMapper;
import ca.utoronto.lms.subject.model.Subject;
import ca.utoronto.lms.subject.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService extends ExtendedService<Subject, SubjectDTO, Long> {
    private final SubjectRepository repository;
    private final SubjectMapper mapper;

    @Autowired private FacultyFeignClient facultyFeignClient;

    public SubjectService(SubjectRepository repository, SubjectMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    protected List<SubjectDTO> mapMissingValues(List<SubjectDTO> subjects) {
        map(
                subjects,
                SubjectDTO::getStudyProgram,
                SubjectDTO::setStudyProgram,
                (ID) -> facultyFeignClient.getStudyProgram(ID));
        map(
                subjects,
                SubjectDTO::getProfessor,
                SubjectDTO::setProfessor,
                (ID) -> facultyFeignClient.getTeacher(ID));
        map(
                subjects,
                SubjectDTO::getAssistant,
                SubjectDTO::setAssistant,
                (ID) -> facultyFeignClient.getTeacher(ID));

        return subjects;
    }

    public List<SubjectDTO> findByStudyProgramId(Long id) {
        List<SubjectDTO> subjects =
                mapper.toDTO(
                        repository.findByStudyProgramIdAndDeletedFalseOrderBySemesterAscNameAsc(
                                id));
        return subjects.isEmpty() ? subjects : this.mapMissingValues(subjects);
    }

    public List<SubjectDTO> findByTeacherUsername(String username) {
        Long teacherId = facultyFeignClient.getTeacherByUsername(username).getId();
        List<SubjectDTO> subjects =
                mapper.toDTO(
                        repository
                                .findByProfessorIdOrAssistantIdAndDeletedFalseOrderBySemesterAscNameAsc(
                                        teacherId, teacherId));
        return subjects.isEmpty() ? subjects : this.mapMissingValues(subjects);
    }

    public SubjectDTO updateSyllabus(Long id, String syllabus) {
        Subject subject = repository.findById(id).orElse(null);
        if (subject == null) {
            return null;
        }

        if (!SecurityUtils.hasAuthority(SecurityUtils.ROLE_ADMIN)) {
            Long teacherId = SecurityUtils.getTeacherId();
            if (!teacherId.equals(subject.getProfessorId())
                    && !teacherId.equals(subject.getAssistantId())) {
                // TODO: implement proper exception
                throw new RuntimeException(
                        "You are not authorized to update this subject's syllabus.");
            }
        }

        subject.setSyllabus(syllabus);
        return mapper.toDTO(repository.save(subject));
    }
}
