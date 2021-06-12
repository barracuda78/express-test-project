package ru.eforward.express_testing.testingProcess.questionHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

public class DescriptionHandler extends Handler implements QuestionHandler{
    @Override
    public boolean canProcess(QuestionType enumValue) {
        return enumValue == QuestionType.DESCRIPTION;
    }

    @Override
    public String process(String q) {
        LogHelper.writeMessage("--class DescriptionHandler--");
        //1. get question name if exists:
        String questionName = getQuestionName(q);
        LogHelper.writeMessage("--questionName = " + questionName);
        //2.get question itself:
        String questionItself = getQuestion(q);
        LogHelper.writeMessage("--questionItself = " + questionItself);
        //3. get all variants of answer:
        String allVariants = getAllVariants(q);

        //6. build the html-string here:
        StringBuilder sb = startBuildingHtml(questionName, questionItself);

        sb.append("<input class=\"button\" type=\"submit\" name=\"descriptionButton\" value=\"Далее\">");
        sb.append("<input type=\"hidden\" name=\"type\" value=\"DESCRIPTION\">");
        sb.append("</form>");
        sb.append("</p>");
        return sb.toString();
    }
}
