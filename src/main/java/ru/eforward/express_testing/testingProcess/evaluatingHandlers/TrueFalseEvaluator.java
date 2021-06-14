package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrueFalseEvaluator implements EvaluatingHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(TrueFalseEvaluator.class);

    @Override
    public boolean canEvaluate(QuestionType enumType) {
        return enumType == QuestionType.TRUE_FALSE;
    }

    //Грант похоронен в могиле Гранта.{F}
    //
    //Солнце встает на Востоке.{T}
    @Override
    public int evaluate(String question, String answer) {
        //todo: implement evaluation of TRUE_FALSE question types.
        //answer may by {F} or {T}

        LOGGER.info("evaluating: answer = " + answer);

        if(answer == null || question == null){
            return 0;
        }

        question = question.trim().toLowerCase();
        answer = answer.trim().toLowerCase();

        if(answer.isEmpty()){
            return 0;
        }

        String correctAnswer = "";
        if(question.contains("{") && question.contains("}")){
            correctAnswer = question.substring(question.indexOf("{"), question.indexOf("}") + 1);
        }

        LogHelper.writeMessage("correctAnswer = " + correctAnswer);
        return correctAnswer.equals(answer) ? 10 : 0;
    }
}
