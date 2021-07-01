package ru.eforward.express_testing.model.school;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Lesson {
    private int id;
    private String lessonName;
    private String pathToTestFile;
    private int levelId;
}