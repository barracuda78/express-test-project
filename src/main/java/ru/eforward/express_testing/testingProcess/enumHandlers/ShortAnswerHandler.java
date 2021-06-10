package ru.eforward.express_testing.testingProcess.enumHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

import java.util.Objects;

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
        LogHelper.writeMessage("-- class DefaultHandler ");
        //1. get question name if exists:
        String questionName = getQuestionName(q); //methods are inherited from superclass Handler
        //2.get question itself:
        String questionItself = getQuestion(q);
        //3. get all variants of answer:
        String allVariants = getAllVariants(q);

        //6. build the html-string here:
        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
        sb.append("<b>");
        sb.append(questionName);
        sb.append("</b>");
        sb.append("<br>");
        sb.append("<b>");
        sb.append(questionItself);
        sb.append("</b>");
            sb.append("<form method=\"get\" action=\"AnswerHandlerServlet\" method=\"GET\">" );
            sb.append("<input type=\"text\" required placeholder=\"напишите ваш ответ\" name=\"shortAnswer\"><br>");
            sb.append("<input class=\"button\" type=\"submit\" name=\"choice1\" value=\"Отправить\">");
        sb.append("</form>");
        sb.append("</p>");
        return sb.toString();
    }
}
