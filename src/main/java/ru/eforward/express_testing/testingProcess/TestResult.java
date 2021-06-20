package ru.eforward.express_testing.testingProcess;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TestResult {
    private int id;
    private int studentId;
    private int schoolId;
    private int lessonId;
    //map like: QUESTION - ANSWER+scoreOfAnswer
    private Map<String, String> map = new LinkedHashMap<>();
    private int totalScore;
    //todo: do I need here int maxScore (to know what was maximum for this test?)
    // or create counting method for it by multiplying size * 10 ?

    /**
     * Sets a total amount of scores could be earned by student for this test.
     * @param totalScore
     */
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
