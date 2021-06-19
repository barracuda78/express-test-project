package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;

public interface EvaluatingHandler {
    /**
     *Takes a QuestionType enum value, finds out if this value can be processed with this class.
     * @param enumType - a QuestionType enum value.
     * @return true if this type of question can be processed with this class, otherwise returns false;
     */
    boolean canEvaluate(QuestionType enumType);
    /**
     *Takes question and answer and compares and evaluates the answer
     * @param question - plain string (one whole question of the test).
     * @param answer - plain string - the given by the Student answer.
     * @return integer value as a score for this particular question;
     */
    int evaluate(String question, String answer);


}
