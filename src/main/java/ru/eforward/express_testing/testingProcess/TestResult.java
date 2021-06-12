package ru.eforward.express_testing.testingProcess;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class TestResult {
    private int id;
    private int studentId;
    private int schoolId;
    private int lessonId;
    private Map<String, String> map = new LinkedHashMap<>();
    private int totalScore;

    public synchronized void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
