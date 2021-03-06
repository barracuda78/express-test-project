package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.testingProcess.QuestionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComplianceEvaluator implements EvaluatingHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(ComplianceEvaluator.class);

    @Override
    public boolean canEvaluate(QuestionType enumType) {
        return enumType == QuestionType.COMPLIANCE;
    }

    @Override
    public int evaluate(String q, String a) {
        if(q == null || a == null){
            return 0;
        }

        //separator for converting to list is =$=
        String[] qArray = q.split("=\\$=");
        String[] aArray = a.split("=\\$=");

        List<String> questions = new ArrayList<>(Arrays.asList(qArray));
        List<String> answers = new ArrayList<>(Arrays.asList(aArray));

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
        //all answers correct - 10. to avoid loosing scores while rounding to an integer
        return correctAnswersCounter == questions.size() ? 10 : score;
    }


}