package ru.eforward.express_testing.model.school;

public class TestResult {
    private int id;
    private String name;
    private String textResult;
    private int score;

    public TestResult() {
    }

    public TestResult(int id, String name, String textResult, int score) {
        this.id = id;
        this.name = name;
        this.textResult = textResult;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextResult() {
        return textResult;
    }

    public void setTextResult(String textResult) {
        this.textResult = textResult;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "TestResult " + name + ", score: " + score;
    }
}
