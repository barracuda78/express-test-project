package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.testingProcess.TestingUnit;
import ru.eforward.express_testing.utils.LogHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiChoiceEvaluator implements EvaluatingHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiChoiceEvaluator.class);
    @Override
    public boolean canEvaluate(QuestionType enumType) {
        return enumType == QuestionType.MULTICHOICE;
    }

    //Ленин {~похоронен =родился ~живет} в Симбирске.

    //День Благодарения празднуется во {
    //    ~второй
    //    ~третий
    //    =четвертый
    //} вторник ноября.
    //
    //Японские символы сначала появились в:? {
    //    ~Индии
    //    =Китае
    //    ~Kорее
    //    ~Египте
    //}
    @Override
    public int evaluate(String question, String answer) {
        LOGGER.info("evaluating: answer = " + answer);

        question = question.trim().toLowerCase();
        answer = answer.trim().toLowerCase();

        //lazy quantifier used (for one-lined questions)
        Pattern p = Pattern.compile("=.+?\\s"); //regex matches to every combination started with '=' and ended with 'space' symbol (including \n, \t, \r\n)
        Matcher m = p.matcher(question);

        String correctAnswer = "";
        if(m.find()){
            correctAnswer = m.group();
            correctAnswer = correctAnswer.substring(1); //removing '=' sign from the beginning of this string;
            correctAnswer = correctAnswer.replaceAll("\\s", "");
        }
        return correctAnswer.equals(answer) ? 10 : 0;
    }
}