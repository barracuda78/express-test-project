package ru.eforward.express_testing.testingProcess.questionHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

public class UndefinedHandler extends Handler implements QuestionHandler{
    //always returns false : will be used only if no other handlers can process
    @Override
    public boolean canProcess(QuestionType enumValue) {
        return false;
    }

    //does nothing but returns the same string
    @Override
    public String process(String q) {
        LogHelper.writeMessage("--UndefinedHandler--");
        //1. get question name if exists:
        String questionName = "Вопрос на удачу:";
        //2.get question itself:
        String questionItself = "Назовите фамилию, имя и отчество вашего преподавателя";

        //3. build the html-string here:
        StringBuilder sb = startBuildingHtml(questionName, questionItself);

        sb.append("<input type=\"text\" required placeholder=\"напишите ваш ответ\" name=\"undefinedAnswer\"><br>");
        sb.append("<input class=\"button\" type=\"submit\" name=\"undefinedButton\" value=\"Отправить\">");
        sb.append("<input type=\"hidden\" name=\"type\" value=\"UNDEFINED\">");
        sb.append("</form>");
        sb.append("</p>");
        return sb.toString();
    }
}
