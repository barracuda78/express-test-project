package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.testingProcess.QuestionType;
import ru.eforward.express_testing.utils.LogHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplianceEvaluator implements EvaluatingHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(ComplianceEvaluator.class);

    @Override
    public boolean canEvaluate(QuestionType enumType) {
        return enumType == QuestionType.COMPLIANCE;
    }

//    Укажите столицы государств: {
//    =Канада -> Оттава
//                =Италия -> Рим
//                =Япония -> Токио
//                =Индия  -> Нью Дели
//    }

    @Override
    public int evaluate(String q, String a) {
        LOGGER.info("ComplianceEvaluator evaluating: answer = " + a);
        if(q == null || a == null){
            return 0;
        }

        //separator for converting to list is =$=

        String[] qArray = q.split("=\\$=");
        String[] aArray = a.split("=\\$=");

        List<String> questions = new ArrayList<>(Arrays.asList(qArray));
        List<String> answers = new ArrayList<>(Arrays.asList(aArray));
        LOGGER.info("ComplianceEvaluator evaluate() : q = \n" + q);
        LOGGER.info("ComplianceEvaluator evaluate() questions = " + questions);
        LOGGER.info("ComplianceEvaluator evaluate() answers = " + answers);

        int score = 0;
        int correctAnswersCounter = 0;


        for(int i = 0; i < answers.size(); i++){
            String question = questions.get(i);
            String answer = answers.get(i);
            question = question.trim().toLowerCase();
            answer = answer.trim().toLowerCase();

            if(answer.isEmpty()){
                continue; //do not add scores for this subquestion.
            }

            if(answer.equals(question)){
                score = score + 10/questions.size();
                correctAnswersCounter ++;
            }
        }

        return correctAnswersCounter == questions.size() ? 10 : score; //all answers correct - 10. to avoid loosing scores while rounding to an integer
    }


}