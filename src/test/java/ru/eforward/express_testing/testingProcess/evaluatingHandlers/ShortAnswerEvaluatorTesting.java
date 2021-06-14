package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.eforward.express_testing.testingProcess.QuestionType;

public class ShortAnswerEvaluatorTesting {
    @Test
    public void canEvaluateTesting(){
        ShortAnswerEvaluator shortAnswerEvaluator = new ShortAnswerEvaluator();

        Assertions.assertEquals(false,
                shortAnswerEvaluator.canEvaluate(QuestionType.MULTICHOICE),
                "Should return false with MULTICHOICE type as a parameter");

        Assertions.assertTrue(shortAnswerEvaluator.canEvaluate(QuestionType.SHORT_ANSWER),
                "Should return true with SHORT_ANSWER type as a parameter");

        Assertions.assertFalse(shortAnswerEvaluator.canEvaluate(QuestionType.COMPLIANCE),
                "Should return false with COMPLIANCE type as a parameter");

        Assertions.assertFalse(shortAnswerEvaluator.canEvaluate(QuestionType.DESCRIPTION),
                "Should return false with DESCRIPTION type as a parameter");

        Assertions.assertFalse(shortAnswerEvaluator.canEvaluate(QuestionType.TRUE_FALSE),
                "Should return false with TRUE_FALSE type as a parameter");

        Assertions.assertFalse(shortAnswerEvaluator.canEvaluate(QuestionType.ESSAY),
                "Should return false with ESSAY type as a parameter");

        Assertions.assertFalse(shortAnswerEvaluator.canEvaluate(QuestionType.NUMBER),
                "Should return false with NUMBER type as a parameter");

        Assertions.assertFalse(shortAnswerEvaluator.canEvaluate(QuestionType.COMMENT),
                "Should return false with COMMENT type as a parameter");

        Assertions.assertFalse(shortAnswerEvaluator.canEvaluate(QuestionType.UNDEFINED),
                "Should return false with UNDEFINED type as a parameter");
    }

    @Test
    public void evaluateTest(){
        ShortAnswerEvaluator sa = new ShortAnswerEvaluator();
        String question = "Кто похоронен в могиле Гранта?{=никто =никого}";

        String answer = "никто";
        Assertions.assertEquals(10, sa.evaluate(question, answer));
        answer = "никого";
        Assertions.assertEquals(10, sa.evaluate(question, answer));
        answer = "НИКОГО   ";
        Assertions.assertEquals(10, sa.evaluate(question, answer));
        answer = "  НИКТО   ";
        Assertions.assertEquals(10, sa.evaluate(question, answer));
        answer = "Вася";
        Assertions.assertEquals(0, sa.evaluate(question, answer));
        answer = "";
        Assertions.assertEquals(0, sa.evaluate(question, answer));
        answer = null;
        Assertions.assertEquals(0, sa.evaluate(question, answer));
    }
}
