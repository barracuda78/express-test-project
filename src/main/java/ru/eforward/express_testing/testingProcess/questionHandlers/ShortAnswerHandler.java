package ru.eforward.express_testing.testingProcess.questionHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

public class ShortAnswerHandler extends Handler implements QuestionHandler{

    @Override
    public boolean canProcess(QuestionType enumValue) {
        return enumValue == QuestionType.SHORT_ANSWER;
    }

    /**
     * A String, passed as a parameter to this method, looks like:
     *
     *      Кто похоронен в могиле Гранта?{=никто =никого}
     *
     * or like this:
     *
     *      два плюс два равно {=четыре =4}
     *
     * if only one answer is shown - no '=' sign required.
     * */
    @Override
    public String process(String q) {
        LogHelper.writeMessage("--class ShortAnswerHandler");
        //1. get question name if exists:
        String questionName = getQuestionName(q); //methods are inherited from superclass Handler
        //2.get question itself:
        String questionItself = getQuestion(q);
        //3. get all variants of answer:
        String allVariants = getAllVariants(q);

        //6. build the html-string here:
        StringBuilder sb = startBuildingHtml(questionName, questionItself);

            sb.append("<input type=\"text\" required placeholder=\"напишите ваш ответ\" name=\"shortAnswer\"><br>");
            sb.append("<input class=\"button\" type=\"submit\" name=\"choice2\" value=\"Отправить\">");
        sb.append("</form>");
        sb.append("</p>");
        return sb.toString();
    }
}
