package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;

public interface EvaluatingHandler {
    boolean canEvaluate(QuestionType enumType);
    int evaluate(String question, String answer);


}
