package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiсhoiceEvaluator implements EvaluatingHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiсhoiceEvaluator.class);
    @Override
    public boolean canEvaluate(QuestionType enumType) {
        return enumType == QuestionType.MULTICHOICE;
    }

    @Override
    public int evaluate(String question, String answer) {
        LOGGER.info("evaluating: answer = " + answer);

        if(answer == null  || question == null){
            return 0;
        }

        question = question.trim().toLowerCase();
        answer = answer.trim().toLowerCase();

        if(answer.isEmpty()){
            return 0;
        }

        //todo: implement percentage and lined-comments here ~%50%Галилея#Вы должны быть более определенным.
        //lazy quantifier used (for one-lined questions)
        //regex matches to every combination started with '=' and ended with 'space' symbol (including \n, \t, \r\n)
        Pattern p = Pattern.compile("=.+?\\s");
        Matcher m = p.matcher(question);

        String correctAnswer = "";
        if(m.find()){
            correctAnswer = m.group();
            correctAnswer = correctAnswer.substring(1); //removing '=' sign from the beginning of this string;
            correctAnswer = correctAnswer.replaceAll("\\s", "");
            correctAnswer = correctAnswer.replaceAll("}", "");
        }
        LogHelper.writeMessage("correctAnswer = " + correctAnswer);
        return correctAnswer.equals(answer) ? 10 : 0;
    }
}