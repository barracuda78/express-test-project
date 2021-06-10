package ru.eforward.express_testing.model.testingProcess.enumHandlers;

import ru.eforward.express_testing.model.testingProcess.QuestionType;

public class MultichoiceHandler implements QuestionHandler{
    @Override
    public boolean canProcess(QuestionType enumValue) {
        return enumValue == QuestionType.MULTICHOICE;
    }

    @Override
    public String process(String s) {
        return null;
    }
}
