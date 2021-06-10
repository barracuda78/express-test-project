package ru.eforward.express_testing.model.testingProcess.enumHandlers;

import ru.eforward.express_testing.model.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

import javax.mail.search.SearchTerm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        LogHelper.writeMessage("---class MultichoiceHandler method process() q = \n" + q);
        //todo: parse the given parameter to appropriate htmlString:
        //1. remove all \n signs to get just a one-lined String:
        //q = q.replaceAll("\n", "");
        //2. get all variants of answer:
        String questionName = q.contains("::") ? q.substring(q.indexOf("::"), q.lastIndexOf("::")) : "Вопрос:";
        String allVariants = q.substring(q.indexOf('{') + 1, q.indexOf('}'));

//        String[] variantsArray = allVariants.split("[\\s]+");
//        //first and last units of this array are emptyStrings in case of multilined-question format.
//        // I have to remove empty strings:
//        List<String> variants = new ArrayList<>(Arrays.asList(variantsArray));

        LogHelper.writeMessage("---class MultichoiceHandler method process() allVariants =\n" + allVariants);
        //3. get the question itself:
        String questionItself = q.substring(0, q.indexOf('{')) + q.substring(q.indexOf('}') + 1);
        StringBuilder sb = new StringBuilder();

        sb.append("<p>");
            sb.append("<b>");
                sb.append(questionName);
            sb.append("</b>");
            sb.append("<b>");
                sb.append(questionItself);
            sb.append("</b>");
            sb.append("<ul>");
            sb.append("<form method=\"get\" action=\"AnswerHandlerServlet\">");  //TODO:------------->have to implement logic this servlet
                for(String variant : allVariants.split("[\\s]+")){
                    if("".equals(variant)){
                        continue;
                    }
                    LogHelper.writeMessage("variant = " + variant);
                    sb.append("<li>");
                    if(variant.contains("%")){
                        variant = variant.substring(variant.lastIndexOf("%"));
                        sb.append(variant); //taking into account variant may start not only with '=' or '~' signs, but '~%50%';
                    }else{
                        sb.append(variant.substring(1)); //taking into account variant starts with '~' sign or '=' sign;
                    }
                    sb.append("<input type=\"text\" required placeholder=\"type your answer\" name=\"answer\"><br>"); //todo: radiobutton instead of text here!
                    sb.append("</li>");
                }
                sb.append("</ul>");
                sb.append("<input class=\"button\" type=\"submit\" name=\"answeredButton\" value=\"Отправить\">");
            sb.append("</form>");

        sb.append("</p>");
        return sb.toString();
    }
}
