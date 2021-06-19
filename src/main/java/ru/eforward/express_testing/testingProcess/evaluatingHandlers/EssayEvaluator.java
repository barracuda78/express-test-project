package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;

public class EssayEvaluator implements EvaluatingHandler{
    @Override
    public boolean canEvaluate(QuestionType enumType) {
        return enumType == QuestionType.ESSAY;
    }

    @Override
    public int evaluate(String question, String answer) {
        //for further development: check with the customer minimum essay size requirement
        // and minimum essay punctuation. Then evaluate by given parameters
        if(answer == null || answer.trim().equals("")){
            return 0;
        }
        return 10;
    }
}
