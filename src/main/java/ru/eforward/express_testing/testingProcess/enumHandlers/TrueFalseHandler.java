package ru.eforward.express_testing.testingProcess.enumHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

public class TrueFalseHandler extends Handler implements QuestionHandler{
    @Override
    public boolean canProcess(QuestionType enumValue) {
        return enumValue == QuestionType.TRUE_FALSE;
    }


    //Грант похоронен в могиле Гранта.{F}
    //
    //Солнце встает на Востоке.{T}
    @Override
    public String process(String q) {
        LogHelper.writeMessage("---class TrueFalseHandler");
        //1. get question name if exists:
        String questionName = getQuestionName(q); //methods are inherited from superclass Handler
        //2.get question itself:
        String questionItself = getQuestion(q);
        //3. get all variants of answer:
        //String allVariants = getAllVariants(q); //- there is no variants in this type of question (true-false only)

        //4. build the html-string here:
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
        sb.append("<input type=\"radio\" name=\"choice3\" value=\"trueCase\">ДА<br>");
        sb.append("<input type=\"radio\" name=\"choice3\" value=\"falseCase\">НЕТ<br>");
        sb.append("<input class=\"button\" type=\"submit\" name=\"choice3\" value=\"Отправить\">");
        sb.append("</form>");
        sb.append("</p>");
        return sb.toString();
    }
}
