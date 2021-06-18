package ru.eforward.express_testing.testingProcess.questionHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;


/**
 * This class does not support multianswer number questions
 * Subtypes of questions supported:
 * 1. Когда родился Грант? {#1822} - NumberQuestionType.SINGLE
 * 2. Значение числа Пи (4 цифры после запятой)? {#3.1415:0.0005} - NumberQuestionType.PRECISION
 * 3. Значение числа Пи (3 цифры после запятой)? {#3.141..3.142} - NumberQuestionType.DIAPASON
 * */
public class NumberHandler extends Handler implements QuestionHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberHandler.class);

    @Override
    public boolean canProcess(QuestionType enumValue) {
        return enumValue == QuestionType.NUMBER;
    }

    @Override
    public String process(String q) {
        LOGGER.info("processing question: " + q);
        LogHelper.writeMessage("--NumberHandler--");
        //1. get question name if exists:
        String questionName = getQuestionName(q);
        //2.get question itself:
        String questionItself = getQuestion(q);
        //3. build the html-string here:
        StringBuilder sb = startBuildingHtml(questionName, questionItself);

        sb.append("<input type=\"text\" required placeholder=\"напишите ваш ответ\" name=\"NumberAnswer\"><br>");
        sb.append("<input class=\"button\" type=\"submit\" name=\"numberButton\" value=\"Отправить\">");
        sb.append("<input type=\"hidden\" name=\"type\" value=\"NUMBER\">");
        sb.append("</form>");
        sb.append("</p>");
        return sb.toString();
    }

    /**
     * This nested enum determines subtypes of a NumberQuestionType
     * */
    public enum NumberQuestionType{
        SINGLE,    //for this type: 'Когда родился Грант? {#1822}'
        PRECISION, //for this one: 'Значение числа Пи (4 цифры после запятой)? {#3.1415:0.0005}'
        DIAPASON   //for 'Значение числа Пи (3 цифры после запятой)? {#3.141..3.142}'
    }


}