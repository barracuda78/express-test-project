package ru.eforward.express_testing.model.testingProcess.enumHandlers;

import ru.eforward.express_testing.model.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

import java.util.Objects;

public class MultichoiceHandler implements QuestionHandler{
    @Override
    public boolean canProcess(QuestionType enumValue) {
        return enumValue == QuestionType.MULTICHOICE;
    }

    /**
     * A String, passed as a paramether to this method, looks like:
     * Кто похоронен в могиле Гранта? {~Грант ~Джефферсон =никто}
     *
     * or like:
     *
     * Ленин {~похоронен =родился ~живет} в Симбирске.
     *
     * or like:
     *
     * День Благодарения празднуется во {
     *     ~второй
     *     ~третий
     *     =четвертый
     * } вторник ноября.
     *
     * */
    @Override
    public String process(String q) {
        //todo: parse the given parameter to appropriate htmlString:
        //1. get question name if exists:
        String questionName = q.contains("::") ? q.substring(q.indexOf("::") + 2, q.lastIndexOf("::")) : "Вопрос";
        //2. get all variants of answer:
        String allVariants = q.substring(q.indexOf('{') + 1, q.indexOf('}'));
        //3. get the question itself:
        int blankSpacesIndex = q.indexOf('{');
        String questionItself = q.substring(0, q.indexOf('{')) + q.substring(q.indexOf('}') + 1);
        //4. check if there is a question text after {...} variants block: insert "____" here:
        if(!q.endsWith("}")){
            StringBuilder questSB = new StringBuilder(questionItself);
            questSB.insert(blankSpacesIndex, "_____"); //
            questionItself = questSB.toString();
        }
        //5. remove ::abcd...:: block if exists:
        if(questionItself.contains("::")){
            questionItself = questionItself.substring(questionItself.lastIndexOf("::") + 2);
        }
        //6. add ':' after questionName:
        questionItself = questionItself + ":";

        //7. build the html-string here:
        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
            sb.append("<b>");
                sb.append(questionName);
            sb.append("</b>");
            sb.append("<br>");
            sb.append("<b>");
                sb.append(questionItself);
            sb.append("</b>");
            sb.append("<ul>");
            sb.append("<form method=\"get\" action=\"AnswerHandlerServlet\" method=\"GET\">" );  //TODO:------------->have to implement logic this servlet
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
                    sb.append("<input type=\"radio\" name=\"choice1\" value=\"-123-\">" + variant + "<br>");
                    sb.append("</li>");
                }
                sb.append("</ul>");
                sb.append("<input class=\"button\" type=\"submit\" name=\"choice1\" value=\"Отправить\">");
            sb.append("</form>");
        sb.append("</p>");
        return sb.toString();
    }
}
