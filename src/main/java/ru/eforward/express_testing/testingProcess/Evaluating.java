package ru.eforward.express_testing.testingProcess;

public interface Evaluating {
    /**
     * Calculates and returns particular amount of score.
     * @param type - a given QuestionType,
     * @param question - the plain string of a single question of the test.
     * @param choice - the string representation of student's answer.
     * @return the integer amount of score for this question of a test.
     */
    int getScore(QuestionType type, String question, String choice);
}