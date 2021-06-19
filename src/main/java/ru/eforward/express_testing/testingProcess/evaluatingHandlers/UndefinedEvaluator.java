package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.testingProcess.QuestionType;

public class UndefinedEvaluator implements EvaluatingHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(UndefinedEvaluator.class);

    @Override
    public boolean canEvaluate(QuestionType enumType) {
        return false;
    }

    @Override
    public int evaluate(String question, String answer) {
        LOGGER.error("UndefinedEvaluator evaluate method evaluates question = " + question + ", answer = " + answer);
        return -999;
    }
}
