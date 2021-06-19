package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;

public class CommentEvaluator implements EvaluatingHandler{
    @Override
    public boolean canEvaluate(QuestionType enumType) {
        return enumType == QuestionType.COMMENT;
    }

    @Override
    public int evaluate(String question, String answer) {
        return 0;
    }
}
