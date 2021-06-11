package ru.eforward.express_testing.testingProcess.enumHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

import java.util.Objects;

public class MultichoiceHandler extends Handler implements QuestionHandler{

    @Override
    public boolean canProcess(QuestionType enumValue) {
        return enumValue == QuestionType.MULTICHOICE;
    }
//How does this type of question look:

//Кто похоронен в могиле Гранта? {~Грант ~Джефферсон =никто}

//Ленин {~похоронен =родился ~живет} в Симбирске.

//День Благодарения празднуется во {
//    ~второй
//    ~третий
//    =четвертый
//} вторник ноября.
    @Override
    public String process(String q) {
        LogHelper.writeMessage("---MultichoiceHandler");

        //1. get question name if exists:
        String questionName = getQuestionName(q); //methods are inherited from superclass Handler
        //2.get question itself:
        String questionItself = getQuestion(q);
        //3. get all variants of answer:
        String allVariants = getAllVariants(q);

        //6. build the html-string here:
        StringBuilder sb = startBuildingHtml(questionName, questionItself);

                sb.append("<ul>");
                for (String variant : allVariants.split("[~=]+")) {
                    if (Objects.nonNull(variant) && "".equals(variant.trim())) {
                        continue;   //first and last units of this array are emptyStrings in case of multilined-question format. removing them:
                    }
                    LogHelper.writeMessage("variant = " + variant);
                    sb.append("<li>");
                    if (variant.contains("%")) {
                        variant = variant.substring(variant.lastIndexOf("%")); //taking into account variant may start not only with '=' or '~' signs, but '~%50%';
                        //sb.append(variant);
                    }
                    sb.append("<input type=\"radio\" name=\"choice1\" value=\"" + variant + "\">" + variant + "<br>");
                    sb.append("</li>");
                }
                sb.append("</ul>");
                sb.append("<input class=\"button\" type=\"submit\" name=\"choice1\" value=\"Отправить\">");
                sb.append("<input type=\"hidden\" name=\"type\" value=\"MULTICHOICE\">");

            sb.append("</form>");
        sb.append("</p>");
        return sb.toString();
    }
}