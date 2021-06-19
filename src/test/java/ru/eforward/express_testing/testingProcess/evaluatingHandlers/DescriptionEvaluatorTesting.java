package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.eforward.express_testing.testingProcess.QuestionType;

public class DescriptionEvaluatorTesting {

    @Test
    public void canEvaluateTesting(){
        DescriptionEvaluator de = new DescriptionEvaluator();

        Assertions.assertEquals(false, de.canEvaluate(QuestionType.MULTICHOICE),
                "Should return false with MULTICHOICE type as a parameter");

        Assertions.assertFalse(de.canEvaluate(QuestionType.SHORT_ANSWER),
                "Should return false with SHORT_ANSWER type as a parameter");

        Assertions.assertFalse(de.canEvaluate(QuestionType.COMPLIANCE),
                "Should return false with COMPLIANCE type as a parameter");

        Assertions.assertTrue(de.canEvaluate(QuestionType.DESCRIPTION),
                "Should return true with DESCRIPTION type as a parameter");

        Assertions.assertFalse(de.canEvaluate(QuestionType.TRUE_FALSE),
                "Should return false with TRUE_FALSE type as a parameter");

        Assertions.assertFalse(de.canEvaluate(QuestionType.ESSAY),
                "Should return false with ESSAY type as a parameter");

        Assertions.assertFalse(de.canEvaluate(QuestionType.NUMBER),
                "Should return false with NUMBER type as a parameter");

        Assertions.assertFalse(de.canEvaluate(QuestionType.COMMENT),
                "Should return false with COMMENT type as a parameter");

        Assertions.assertFalse(de.canEvaluate(QuestionType.UNDEFINED),
                "Should return false with UNDEFINED type as a parameter");
    }

    @Test
    public void evaluateTest(){
        DescriptionEvaluator de = new DescriptionEvaluator();
        String question = "Этот блок вопросов касается физики.";

        String answer = "ясно";
        Assertions.assertEquals(0, de.evaluate(question, answer));
        answer = "";
        Assertions.assertEquals(0, de.evaluate(question, answer));
        answer = null;
        Assertions.assertEquals(0, de.evaluate(question, answer));
    }
}