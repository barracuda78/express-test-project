package ru.eforward.express_testing.testingProcess.enumHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

public class DefaultHandler implements QuestionHandler{
    //always returns false : will be used only if no other handlers can process
    @Override
    public boolean canProcess(QuestionType enumValue) {
        return false;
    }

    //does nothing but returns the same string
    @Override
    public String process(String s) {
        LogHelper.writeMessage("--class DefaultHandler--");
        return s + " from DefaultHandler";
    }
}
