package ru.eforward.express_testing.testingProcess.enumHandlers;

import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberHandler extends Handler implements QuestionHandler{
    @Override
    public boolean canProcess(QuestionType enumValue) {
        return enumValue == QuestionType.NUMBER;
    }

    //Когда родился Грант? {#1822}
    //
    //Значение числа Пи (4 цифры после запятой)? {#3.1415:0.0005}. //--->погрешность.
    //
    //Значение числа Пи (3 цифры после запятой)? {#3.141..3.142}. //--->диапазон
    @Override
    public String process(String q) {
        LogHelper.writeMessage("--NumberHandler--");
        //1. get question name if exists:
        String questionName = getQuestionName(q);
        //2.get question itself:
        String questionItself = getQuestion(q);
        //3. get all variants of answer:
        String allVariants = getAllVariants(q);

        List<Double> numbers = getNumbers(q); //todo: pass it to validate Test.

        //4. build the html-string here:
        StringBuilder sb = startBuildingHtml(questionName, questionItself);

        sb.append("<input type=\"text\" required placeholder=\"напишите ваш ответ\" name=\"NumberAnswer\"><br>");
        sb.append("<input class=\"button\" type=\"submit\" name=\"numberButton\" value=\"Отправить\">");
        sb.append("<input type=\"hidden\" name=\"type\" value=\"NUMBER\">");
        sb.append("</form>");
        sb.append("</p>");
        return sb.toString();
    }

    //checks if this question has diapason
    private NumberQuestionType getType(String q){
        String numbers = q;
        if(q.contains("{")){
            numbers = getPartWithNumbers(q);
        }

        if(numbers.contains("..")){
            return NumberQuestionType.DIAPASON;
        }
        else if(numbers.contains(":")){
            return NumberQuestionType.PRECISION;
        }
        else{
            return NumberQuestionType.SINGLE;
        }
    }

    private String getPartWithNumbers(String q){
        return q.substring(q.indexOf("#") + 1, q.indexOf("}"));
    }

    private List<Double> getNumbers(String q){
        List<Double> numbers = new ArrayList<>();
        q = getPartWithNumbers(q);
        String[] array = null;
        if(getType(q) == NumberQuestionType.PRECISION){
            array = q.split(":");
        }else if(getType(q) == NumberQuestionType.DIAPASON){
            array = q.split("\\.\\.");
        }else{
            array = new String[1];
            array[0] = q;
        }

        System.out.println("q = " + q);
        System.out.println("String[] array = " + Arrays.toString(array));

        for(String n : array){
            numbers.add(Double.parseDouble(n));
        }
        return numbers;
    }

    public static void main(String[] args) {
        NumberHandler nh = new NumberHandler();
        List<Double> numbers = nh.getNumbers("Значение числа Пи (3 цифры после запятой)? {#355}.");
        System.out.println("numbers List<> = " + numbers);
    }

    enum NumberQuestionType{
        SINGLE,
        PRECISION,
        DIAPASON
    }
}