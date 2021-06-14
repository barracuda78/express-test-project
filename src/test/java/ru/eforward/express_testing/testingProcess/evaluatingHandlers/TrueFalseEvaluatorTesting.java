package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.eforward.express_testing.testingProcess.QuestionType;

public class TrueFalseEvaluatorTesting {
    @Test
    public void canEvaluateTesting(){
        TrueFalseEvaluator tf = new TrueFalseEvaluator();

        Assertions.assertEquals(false,
                tf.canEvaluate(QuestionType.MULTICHOICE),
                "Should return false with MULTICHOICE type as a parameter");

        Assertions.assertFalse(tf.canEvaluate(QuestionType.SHORT_ANSWER),
                "Should return false with SHORT_ANSWER type as a parameter");

        Assertions.assertFalse(tf.canEvaluate(QuestionType.COMPLIANCE),
                "Should return false with COMPLIANCE type as a parameter");

        Assertions.assertFalse(tf.canEvaluate(QuestionType.DESCRIPTION),
                "Should return false with DESCRIPTION type as a parameter");

        Assertions.assertTrue(tf.canEvaluate(QuestionType.TRUE_FALSE),
                "Should return true with TRUE_FALSE type as a parameter");

        Assertions.assertFalse(tf.canEvaluate(QuestionType.ESSAY),
                "Should return false with ESSAY type as a parameter");

        Assertions.assertFalse(tf.canEvaluate(QuestionType.NUMBER),
                "Should return false with NUMBER type as a parameter");

        Assertions.assertFalse(tf.canEvaluate(QuestionType.COMMENT),
                "Should return false with COMMENT type as a parameter");

        Assertions.assertFalse(tf.canEvaluate(QuestionType.UNDEFINED),
                "Should return false with UNDEFINED type as a parameter");
    }

    @Test
    public void evaluateTest(){
        TrueFalseEvaluator tf = new TrueFalseEvaluator();
        String question = "Грант похоронен в могиле Гранта.{F}";

        String answer = "{F}";
        Assertions.assertEquals(10, tf.evaluate(question, answer));
        answer = "{T}";
        Assertions.assertEquals(0, tf.evaluate(question, answer));
        answer = "";
        Assertions.assertEquals(0, tf.evaluate(question, answer));
        answer = null;
        Assertions.assertEquals(0, tf.evaluate(question, answer));

        question = "Солнце встает на Востоке.{T}";

        answer = "{T}";
        Assertions.assertEquals(10, tf.evaluate(question, answer));
        answer = "{F}";
        Assertions.assertEquals(0, tf.evaluate(question, answer));

        answer = "";
        Assertions.assertEquals(0, tf.evaluate(question, answer));
        answer = null;
        Assertions.assertEquals(0, tf.evaluate(question, answer));
    }
}
