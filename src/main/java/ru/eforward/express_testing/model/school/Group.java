package ru.eforward.express_testing.model.school;

import lombok.*;
import ru.eforward.express_testing.model.Student;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="students")
public class Group {
    private int id;
    private String groupName;
    private int schoolId;
    private int teacherId;
    private List<Student> students;
}