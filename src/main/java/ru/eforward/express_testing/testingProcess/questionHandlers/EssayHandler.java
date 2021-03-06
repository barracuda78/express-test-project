package ru.eforward.express_testing.testingProcess.questionHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

public class EssayHandler extends Handler implements QuestionHandler{
    @Override
    public boolean canProcess(QuestionType enumValue) {
        return enumValue == QuestionType.ESSAY;
    }

    @Override
    public String process(String q) {
        LogHelper.writeMessage("--class EssayHandler--");
        //1. get question name if exists:
        String questionName = getQuestionName(q);
        //2.get question itself:
        String questionItself = getQuestion(q);
        //3. get all variants of answer:
        String allVariants = getAllVariants(q);

        //6. build the html-string here:
        StringBuilder sb = startBuildingHtml(questionName, questionItself);

        sb.append("<textarea name=\"essayText\" rows=\"4\" cols=\"20\"></textarea><br>");
        sb.append("<input class=\"button\" type=\"submit\" name=\"essayButton\" value=\"Отправить\">");
        sb.append("<input type=\"hidden\" name=\"type\" value=\"ESSAY\">");
        sb.append("<input type=\"hidden\" name=\"question\" value=\" " + q + "\">"); //passing the original text of question
        sb.append("</form>");
        sb.append("</p>");
        return sb.toString();
    }
}
