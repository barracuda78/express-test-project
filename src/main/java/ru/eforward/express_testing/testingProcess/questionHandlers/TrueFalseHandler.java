package ru.eforward.express_testing.testingProcess.questionHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

public class TrueFalseHandler extends Handler implements QuestionHandler{
    @Override
    public boolean canProcess(QuestionType enumValue) {
        return enumValue == QuestionType.TRUE_FALSE;
    }

    @Override
    public String process(String q) {
        LogHelper.writeMessage("---class TrueFalseHandler");
        //1. get question name if exists:
        String questionName = getQuestionName(q); //methods are inherited from superclass Handler
        //2.get question itself:
        String questionItself = getQuestion(q);

        //3. build the html-string here:
        StringBuilder sb = startBuildingHtml(questionName, questionItself);

        sb.append("<input type=\"radio\" name=\"choice3\" value=\"trueCase\">ДА<br>");
        sb.append("<input type=\"radio\" name=\"choice3\" value=\"falseCase\">НЕТ<br>");
        sb.append("<input class=\"button\" type=\"submit\" name=\"choice\" value=\"Отправить\">");
        sb.append("<input type=\"hidden\" name=\"type\" value=\"TRUE_FALSE\">");
        sb.append("<input type=\"hidden\" name=\"question\" value=\" " + q + "\">"); //passing the original text of question
        sb.append("</form>");
        sb.append("</p>");
        return sb.toString();
    }
}
