package ru.eforward.express_testing.model.testingProcess.enumHandlers;

import ru.eforward.express_testing.model.testingProcess.QuestionType;

public class DefaultHandler implements QuestionHandler{
    //always returns false : will be used only if no other handlers can process
    @Override
    public boolean canProcess(QuestionType enumValue) {
        return false;
    }

    //does nothing but returns the same string
    @Override
    public String process(String s) {
        return s;
    }
}
