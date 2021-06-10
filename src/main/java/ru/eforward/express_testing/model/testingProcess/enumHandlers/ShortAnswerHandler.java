package ru.eforward.express_testing.model.testingProcess.enumHandlers;

import ru.eforward.express_testing.model.testingProcess.QuestionType;

public class ShortAnswerHandler implements QuestionHandler{

    @Override
    public boolean canProcess(QuestionType enumValue) {
        return enumValue == QuestionType.SHORT_ANSWER;
    }

    /**
     * A String, passed as a parameter to this method, looks like:
     *      Кто похоронен в могиле Гранта?{=никто =никого}
     *or like this:
     *      два плюс два равно {=четыре =4}
     * if only one answer is shown - no '=' sign required.
     * */
    @Override
    public String process(String s) {

        return null;
    }
}
