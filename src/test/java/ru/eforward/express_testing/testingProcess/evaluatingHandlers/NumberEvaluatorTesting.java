package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.eforward.express_testing.testingProcess.QuestionType;

public class NumberEvaluatorTesting {
    @Test
    public void canEvaluateTesting(){
        NumberEvaluator ne = new NumberEvaluator();

        Assertions.assertEquals(false,
                ne.canEvaluate(QuestionType.MULTICHOICE),
                "Should return false with MULTICHOICE type as a parameter");

        Assertions.assertFalse(ne.canEvaluate(QuestionType.SHORT_ANSWER),
                "Should return false with SHORT_ANSWER type as a parameter");

        Assertions.assertFalse(ne.canEvaluate(QuestionType.COMPLIANCE),
                "Should return false with COMPLIANCE type as a parameter");

        Assertions.assertFalse(ne.canEvaluate(QuestionType.DESCRIPTION),
                "Should return false with DESCRIPTION type as a parameter");

        Assertions.assertFalse(ne.canEvaluate(QuestionType.TRUE_FALSE),
                "Should return false with TRUE_FALSE type as a parameter");

        Assertions.assertFalse(ne.canEvaluate(QuestionType.ESSAY),
                "Should return false with ESSAY type as a parameter");

        Assertions.assertTrue(ne.canEvaluate(QuestionType.NUMBER),
                "Should return true with NUMBER type as a parameter");

        Assertions.assertFalse(ne.canEvaluate(QuestionType.COMMENT),
                "Should return false with COMMENT type as a parameter");

        Assertions.assertFalse(ne.canEvaluate(QuestionType.UNDEFINED),
                "Should return false with UNDEFINED type as a parameter");
    }

    @Test
    public void evaluateTest(){
        NumberEvaluator ne = new NumberEvaluator();
        String question = "сколько тебе лет {#42:2}";

        String answer = "42";
        Assertions.assertEquals(10, ne.evaluate(question, answer));

        answer = "43";
        Assertions.assertEquals(10, ne.evaluate(question, answer));
        answer = "44";
        Assertions.assertEquals(10, ne.evaluate(question, answer));
        answer = "45";
        Assertions.assertEquals(0, ne.evaluate(question, answer));
        answer = "41";
        Assertions.assertEquals(10, ne.evaluate(question, answer));
        answer = "41.5";
        Assertions.assertEquals(10, ne.evaluate(question, answer));
        answer = "40";
        Assertions.assertEquals(10, ne.evaluate(question, answer));
        answer = "39";
        Assertions.assertEquals(0, ne.evaluate(question, answer));

        question = "сколько тебе лет {#42.5:0.5}";

        answer = "42.5";
        Assertions.assertEquals(10, ne.evaluate(question, answer));
        answer = "43";
        Assertions.assertEquals(10, ne.evaluate(question, answer));
        answer = "42.9";
        Assertions.assertEquals(10, ne.evaluate(question, answer));
        answer = "42.5";
        Assertions.assertEquals(10, ne.evaluate(question, answer));
        answer = "43";
        Assertions.assertEquals(10, ne.evaluate(question, answer));
        answer = "43.0";
        Assertions.assertEquals(10, ne.evaluate(question, answer));
        answer = "43.1";
        Assertions.assertEquals(0, ne.evaluate(question, answer));
        answer = "42.0";
        Assertions.assertEquals(10, ne.evaluate(question, answer));
        answer = "41.9";
        Assertions.assertEquals(0, ne.evaluate(question, answer));

        answer = "";
        Assertions.assertEquals(0, ne.evaluate(question, answer));

        answer = null;
        Assertions.assertEquals(0, ne.evaluate(question, answer));
    }
}
