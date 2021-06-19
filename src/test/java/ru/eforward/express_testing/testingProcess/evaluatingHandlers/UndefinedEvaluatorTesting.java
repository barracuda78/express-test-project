package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.eforward.express_testing.testingProcess.QuestionType;

public class UndefinedEvaluatorTesting {

    @Test
    public void canEvaluateTesting(){
        UndefinedEvaluator ue = new UndefinedEvaluator();

        Assertions.assertEquals(false,
                ue.canEvaluate(QuestionType.MULTICHOICE),
                "Should return false with MULTICHOICE type as a parameter");

        Assertions.assertFalse(ue.canEvaluate(QuestionType.SHORT_ANSWER),
                "Should return false with SHORT_ANSWER type as a parameter");

        Assertions.assertFalse(ue.canEvaluate(QuestionType.COMPLIANCE),
                "Should return false with COMPLIANCE type as a parameter");

        Assertions.assertFalse(ue.canEvaluate(QuestionType.DESCRIPTION),
                "Should return false with DESCRIPTION type as a parameter");

        Assertions.assertFalse(ue.canEvaluate(QuestionType.TRUE_FALSE),
                "Should return false with TRUE_FALSE type as a parameter");

        Assertions.assertFalse(ue.canEvaluate(QuestionType.ESSAY),
                "Should return false with ESSAY type as a parameter");

        Assertions.assertFalse(ue.canEvaluate(QuestionType.NUMBER),
                "Should return false with NUMBER type as a parameter");

        Assertions.assertFalse(ue.canEvaluate(QuestionType.COMMENT),
                "Should return false with COMMENT type as a parameter");

        Assertions.assertFalse(ue.canEvaluate(QuestionType.UNDEFINED),
                "Should return false with UNDEFINED type as a parameter");
    }

    @Test
    public void evaluateTest(){
        UndefinedEvaluator ue = new UndefinedEvaluator();
        String question = "Ленин {~похоронен =родился ~живет} в Симбирске.";

        String answer = "родился";
        Assertions.assertEquals(-999, ue.evaluate(question, answer));
        answer = "Родился";
        Assertions.assertEquals(-999, ue.evaluate(question, answer));
        answer = "РОДИЛСЯ   ";
        Assertions.assertEquals(-999, ue.evaluate(question, answer));
        answer = "   РОдиЛСЯ   ";
        Assertions.assertEquals(-999, ue.evaluate(question, answer));
        answer = "живет";
        Assertions.assertEquals(-999, ue.evaluate(question, answer));
        answer = "";
        Assertions.assertEquals(-999, ue.evaluate(question, answer));
        answer = null;
        Assertions.assertEquals(-999, ue.evaluate(question, answer));
    }
}