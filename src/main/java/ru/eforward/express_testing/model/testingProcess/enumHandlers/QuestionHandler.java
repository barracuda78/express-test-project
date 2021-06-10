package ru.eforward.express_testing.model.testingProcess.enumHandlers;

import ru.eforward.express_testing.model.testingProcess.QuestionType;

public interface QuestionHandler {
    boolean canProcess(QuestionType enumValue);
    String process(String s);
}
