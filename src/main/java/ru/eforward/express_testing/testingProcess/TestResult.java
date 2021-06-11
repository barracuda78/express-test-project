package ru.eforward.express_testing.testingProcess;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {
    private int id;
    private int studentsId;
    private int schoolId;
    private int lessonId;
    private Map<String, String> map = new LinkedHashMap<>();
    private int score;
}
