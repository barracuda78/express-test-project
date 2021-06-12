package ru.eforward.express_testing.testingProcess.questionHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;

public class CommentHandler extends Handler implements QuestionHandler{
    @Override
    public boolean canProcess(QuestionType enumValue) {
        return enumValue == QuestionType.COMMENT;
    }

    @Override
    public String process(String q) {
        return new DescriptionHandler().process(q);
    }
}
