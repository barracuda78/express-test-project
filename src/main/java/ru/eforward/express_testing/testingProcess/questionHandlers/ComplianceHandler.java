package ru.eforward.express_testing.testingProcess.questionHandlers;

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

        Map<String, String> map = createMapOfQuestionAnswers(q); // map of question-answer.
        List<String> list = createListOfAnswers(q); //for answers only.

        //4. build the html-string here:
        StringBuilder sb = startBuildingHtml(questionName, questionItself);

        sb.append("<table border=\"1\">");
        AtomicInteger valueParameter = new AtomicInteger(0);
        for (Map.Entry<String, String> pair : map.entrySet()) {
            String k = pair.getKey();
            k = k.trim();
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
                    sb.append("<option value=\"" + valueParameter.incrementAndGet() + "\">" + s + "</option>"); // 's' is an answer//
                }
                sb.append("</select>");

                sb.append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        sb.append("<input class=\"button\" type=\"submit\" name=\"choice1\" value=\"Отправить\">");
        sb.append("<input type=\"hidden\" name=\"type\" value=\"COMPLIANCE\">");
        sb.append("<input type=\"hidden\" name=\"question\" value=\" " + q + "\">"); //passing the original text of question
        sb.append("</form>");
        sb.append("</p>");
        return sb.toString();
    }

    public Map<String, String> createMapOfQuestionAnswers(String q){
        String allVariants = getAllVariants(q);
        //creating map of question-answer:
        Map<String, String> map = new HashMap<>();
        String[] vars = allVariants.split("\n");
        for(String v : vars){
            if("".equals(v)){
                continue;
            }
            LogHelper.writeMessage("var = " + v);
            String key = v.substring(1, v.indexOf("->"));
            String value = v.substring(v.indexOf("->") + 2);
            map.put(key, value);
        }
        return map;
    }

    public List<String> createListOfAnswers(String q){
        String allVariants = getAllVariants(q);
        List<String> list = new ArrayList<>(); //for answers only.
        String[] vars = allVariants.split("\n");
        for(String v : vars){
            if("".equals(v)){
                continue;
            }
            String value = v.substring(v.indexOf("->") + 2);
            list.add(value);
        }
        return list;
    }

    //separator for converting to String is =$=
    // - is used by ComplianceEvaluator as this type of question has 3 or more subquestions,
    // but interface works with Strings, not with List<String>
    public String createStringFromList(List<String> list){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < list.size(); i++){
            sb.append(list.get(i));
            if(i != list.size() - 1){
                sb.append("=$=");
            }
        }
        return sb.toString();
    }

}