package ru.eforward.express_testing.testingProcess.enumHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ComplianceHandler extends Handler implements  QuestionHandler{
    @Override
    public boolean canProcess(QuestionType enumValue) {
        return enumValue == QuestionType.COMPLIANCE;
    }

    //Укажите столицы государств: {
    //    =Канада -> Оттава
    //    =Италия -> Рим
    //    =Япония -> Токио
    //    =Индия  -> Нью Дели
    //}
    @Override
    public String process(String q) {
        LogHelper.writeMessage("---class ComplianceHandler");

        //1. get question name if exists:
        String questionName = getQuestionName(q); //methods are inherited from superclass Handler
        //2.get question itself:
        String questionItself = getQuestion(q);
        //3. get all variants of answer:
        String allVariants = getAllVariants(q);

        Map<String, String> map = new HashMap<>();
        List<String> list = new ArrayList<>(); //for answers only.
        String[] vars = allVariants.split("\n");
        for(String v : vars){
            if("".equals(v)){
                continue;
            }
            LogHelper.writeMessage("var = " + v);
            String key = v.substring(1, v.indexOf("->"));
            String value = v.substring(v.indexOf("->") + 2);
            map.put(key, value);
            list.add(value);
        }

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
        sb.append("<table border=\"1\">");

        AtomicInteger valueParameter = new AtomicInteger(0);
        for (Map.Entry<String, String> pair : map.entrySet()) {
            String k = pair.getKey();
            if(k.startsWith("=")){
                k = k.substring(1);
            }
            String v = pair.getValue();
            sb.append("<tr>");
                sb.append("<td>");
                    sb.append(k);
                sb.append("</td>");
                sb.append("<td>");
                    //drop-down menu:
                sb.append("<select name=\"answerDropdowns\">");
                sb.append("<option value=\"\" style=\"display:none\">Выберите ответ</option>");
                for(String s : list) {
                    sb.append("<option value=\"" + valueParameter.incrementAndGet() + "\">" + s + "</option>");
                }
                sb.append("</select>");

                sb.append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        sb.append("<input class=\"button\" type=\"submit\" name=\"choice1\" value=\"Отправить\">");
        sb.append("</form>");
        sb.append("</p>");
        return sb.toString();
    }
}
