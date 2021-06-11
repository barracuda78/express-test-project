package ru.eforward.express_testing.model.school;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    private int id;
    private String lessonName;
    private String pathToTestFile;
    private int levelId;
    private int courseId;
    private int schoolId;
}
