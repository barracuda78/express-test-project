package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.testingProcess.questionHandlers.NumberHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class does not support multianswer number questions
 * Subtypes of questions supported:
 * 1. Когда родился Грант? {#1822} - NumberQuestionType.SINGLE
 * 2. Значение числа Пи (4 цифры после запятой)? {#3.1415:0.0005} - NumberQuestionType.PRECISION
 * 3. Значение числа Пи (3 цифры после запятой)? {#3.141..3.142} - NumberQuestionType.DIAPASON
 * */
public class NumberEvaluator implements EvaluatingHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberEvaluator.class);

    @Override
    public boolean canEvaluate(QuestionType enumType) {
        return enumType == QuestionType.NUMBER;
    }


    @Override
    public int evaluate(String question, String answer) {
        LOGGER.info("evaluating question = " + question + ", answer = " + answer);
        if(answer == null  || question == null){
            return 0;
        }
        question = question.trim().toLowerCase();
        answer = answer.trim().toLowerCase();
        if(answer.isEmpty()){
            return 0;
        }

        //get right answer and it's type:
        List<String> correctAnswers = getCorrectAnswers(question);
        NumberHandler.NumberQuestionType subType = getType(question);

        if(subType == NumberHandler.NumberQuestionType.SINGLE){
            return answer.equals(correctAnswers.get(0)) ? 10 : 0;
        } else if(subType == NumberHandler.NumberQuestionType.DIAPASON){
            //todo: implement NumberQuestionType.DIAPASON case
        } else if(subType == NumberHandler.NumberQuestionType.PRECISION){
            //todo: implement NumberQuestionType.PRECISION case
        }

        //compare answer with rightAnswer

        return 0;
    }

    /**
     * Finds out what subtype of NumberQuestion it is.
     * @param q - a String representation of one single question
     * @return - NumberQuestionType of the given question.
     * */
    NumberHandler.NumberQuestionType getType(String q){
        String numbers = q;
        if(q.contains("{")){
            numbers = getPartWithNumbers(q);
        }

        if(numbers.contains("..")){
            return NumberHandler.NumberQuestionType.DIAPASON;
        }
        else if(numbers.contains(":")){
            return NumberHandler.NumberQuestionType.PRECISION;
        }
        else {
            return NumberHandler.NumberQuestionType.SINGLE;
        }
    }

    /**
     * Gets a question string and returns list of strings containing within curly braces of this question
     * For example, the question is:
     * 'Значение числа Пи (3 цифры после запятой)? {#355:2}'
     * Then it will return list of Strings [355.0, 2.0]
     * @param q - a String representation of one single question
     * @return list of Strings containing within curly braces of this question
     * @throws IllegalArgumentException if a given parameter is not a valid NumberQuestion.
     * */
    private List<String> getCorrectAnswers(String q) throws IllegalArgumentException{
        List<String> numbers = new ArrayList<>();
        q = getPartWithNumbers(q);
        String[] array = null;
        if(getType(q) == NumberHandler.NumberQuestionType.PRECISION){
            array = q.split(":");
        }else if(getType(q) == NumberHandler.NumberQuestionType.DIAPASON){
            array = q.split("\\.\\.");
        }else{
            array = new String[1];
            array[0] = q;
        }

        System.out.println("q = " + q);
        System.out.println("String[] array = " + Arrays.toString(array));

        for(String n : array){
            try {
                Double.parseDouble(n); //uer parseDouble() just to be sure it was really a number literal .
                numbers.add(n);
            }
            catch (NumberFormatException ne){
                LOGGER.warn("NumberQuestion is incorrect: " + q);
                throw new IllegalArgumentException("the number can not be parsed to Double");
            }
        }
        return numbers;
    }

    /**
     * Gets a question string and returns substring witch is within curly braces of this string,
     * without curly braces and without # sign.
     * For example, the given question is:
     * "Значение числа Пи (3 цифры после запятой)? {#355:2}."
     * then it will return '355:2'.
     * @param q - a String representation of one single question
     * @return substring of a given string witch is within curly braces of this string
     * @throws IllegalArgumentException if a given parameter is not a valid NumberQuestion.
     * */
    private String getPartWithNumbers(String q) throws IllegalArgumentException{
        if(q.contains("#") && q.contains("}")){
            return q.substring(q.indexOf("#") + 1, q.indexOf("}"));
        }
        throw new IllegalArgumentException("This might not be a NumberQuestion at all");
    }

    public static void main(String[] args) {
        NumberEvaluator ne = new NumberEvaluator();
        List<String> numbers = ne.getCorrectAnswers("Значение числа Пи (3 цифры после запятой)? {#355.2..2.8}.");
        System.out.println("numbers List<> = " + numbers);
    }
}
