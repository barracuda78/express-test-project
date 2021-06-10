package ru.eforward.express_testing.testingProcess.enumHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;

public interface QuestionHandler {
    boolean canProcess(QuestionType enumValue);
    String process(String s);
}
