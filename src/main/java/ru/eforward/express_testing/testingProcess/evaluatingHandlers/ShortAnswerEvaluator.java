package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShortAnswerEvaluator implements EvaluatingHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(ShortAnswerEvaluator.class);

    @Override
    public boolean canEvaluate(QuestionType enumType) {
        return enumType == QuestionType.SHORT_ANSWER;
    }

    @Override
    public int evaluate(String question, String answer) {
        LOGGER.info("evaluating: answer = " + answer);
        if(answer == null){
            return 0;
        }
        int score = 0;
        question = question.trim().toLowerCase();
        answer = answer.trim().toLowerCase();
        //regex matches to every combination started with '=' and ended with '}' or 'space' symbol (including \n, \t, \r\n)
        Pattern p = Pattern.compile("=.+?[\\s}]");
        Matcher m = p.matcher(question);

        String correctAnswer = "";
        //todo: implement percentage here ~%50%Галилея#Вы должны быть более определенным.
        while(m.find()){
            correctAnswer = m.group();
            correctAnswer = correctAnswer.substring(1); //removing '=' sign from the beginning of this string;
            correctAnswer = correctAnswer.replaceAll("\\s", "");
            correctAnswer = correctAnswer.replaceAll("}", "");
            LogHelper.writeMessage("correctAnswer = " + correctAnswer);
            if(answer.equals(correctAnswer)){
                score = 10;
            }
        }
        return score;
    }
}