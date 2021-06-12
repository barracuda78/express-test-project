package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;

public class UndefinedEvaluator implements EvaluatingHandler{
    @Override
    public boolean canEvaluate(QuestionType enumType) {
        return false;
    }

    @Override
    public int evaluate(String question, String answer) {
        return -999;
    }
}
