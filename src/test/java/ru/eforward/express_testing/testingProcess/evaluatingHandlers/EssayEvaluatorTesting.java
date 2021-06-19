package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.eforward.express_testing.testingProcess.QuestionType;

public class EssayEvaluatorTesting {

    @Test
    public void canEvaluateTesting(){
        EssayEvaluator ee = new EssayEvaluator();

        Assertions.assertEquals(false, ee.canEvaluate(QuestionType.MULTICHOICE),
                "Should return false with MULTICHOICE type as a parameter");

        Assertions.assertFalse(ee.canEvaluate(QuestionType.SHORT_ANSWER),
                "Should return false with SHORT_ANSWER type as a parameter");

        Assertions.assertFalse(ee.canEvaluate(QuestionType.COMPLIANCE),
                "Should return false with COMPLIANCE type as a parameter");

        Assertions.assertFalse(ee.canEvaluate(QuestionType.DESCRIPTION),
                "Should return false with DESCRIPTION type as a parameter");

        Assertions.assertFalse(ee.canEvaluate(QuestionType.TRUE_FALSE),
                "Should return false with TRUE_FALSE type as a parameter");

        Assertions.assertTrue(ee.canEvaluate(QuestionType.ESSAY),
                "Should return true with ESSAY type as a parameter");

        Assertions.assertFalse(ee.canEvaluate(QuestionType.NUMBER),
                "Should return false with NUMBER type as a parameter");

        Assertions.assertFalse(ee.canEvaluate(QuestionType.COMMENT),
                "Should return false with COMMENT type as a parameter");

        Assertions.assertFalse(ee.canEvaluate(QuestionType.UNDEFINED),
                "Should return false with UNDEFINED type as a parameter");
    }

    @Test
    public void evaluateTest(){
        EssayEvaluator ee = new EssayEvaluator();
        String question = "Напишите, кто вы";

        String answer = "Я новичок";
        Assertions.assertEquals(10, ee.evaluate(question, answer));
        answer = "";
        Assertions.assertEquals(0, ee.evaluate(question, answer));
        answer = null;
        Assertions.assertEquals(0, ee.evaluate(question, answer));
    }
}