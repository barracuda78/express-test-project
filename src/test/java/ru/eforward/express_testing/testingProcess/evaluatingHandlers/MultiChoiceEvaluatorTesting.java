package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.eforward.express_testing.testingProcess.QuestionType;

public class MultiChoiceEvaluatorTesting {
    @Test
    public void canEvaluateTesting(){
        MultiChoiceEvaluator multiChoiceEvaluator = new MultiChoiceEvaluator();

        Assertions.assertEquals(true,
                multiChoiceEvaluator.canEvaluate(QuestionType.MULTICHOICE),
                "Should return true with MULTICHOICE type as a parameter");

        Assertions.assertFalse(multiChoiceEvaluator.canEvaluate(QuestionType.SHORT_ANSWER),
                "Should return false with SHORT_ANSWER type as a parameter");

        Assertions.assertFalse(multiChoiceEvaluator.canEvaluate(QuestionType.COMPLIANCE),
                "Should return false with COMPLIANCE type as a parameter");

        Assertions.assertFalse(multiChoiceEvaluator.canEvaluate(QuestionType.DESCRIPTION),
                "Should return false with DESCRIPTION type as a parameter");

        Assertions.assertFalse(multiChoiceEvaluator.canEvaluate(QuestionType.TRUE_FALSE),
                "Should return false with TRUE_FALSE type as a parameter");

        Assertions.assertFalse(multiChoiceEvaluator.canEvaluate(QuestionType.ESSAY),
                "Should return false with ESSAY type as a parameter");

        Assertions.assertFalse(multiChoiceEvaluator.canEvaluate(QuestionType.NUMBER),
                "Should return false with NUMBER type as a parameter");

        Assertions.assertFalse(multiChoiceEvaluator.canEvaluate(QuestionType.COMMENT),
                "Should return false with COMMENT type as a parameter");

        Assertions.assertFalse(multiChoiceEvaluator.canEvaluate(QuestionType.UNDEFINED),
                "Should return false with UNDEFINED type as a parameter");
    }

    @Test
    public void evaluateTest(){
        MultiChoiceEvaluator me = new MultiChoiceEvaluator();
        String question = "Ленин {~похоронен =родился ~живет} в Симбирске.";

        String answer = "родился";
        Assertions.assertEquals(10, me.evaluate(question, answer));
        answer = "Родился";
        Assertions.assertEquals(10, me.evaluate(question, answer));
        answer = "РОДИЛСЯ   ";
        Assertions.assertEquals(10, me.evaluate(question, answer));
        answer = "   РОдиЛСЯ   ";
        Assertions.assertEquals(10, me.evaluate(question, answer));
        answer = "живет";
        Assertions.assertEquals(0, me.evaluate(question, answer));
        answer = "";
        Assertions.assertEquals(0, me.evaluate(question, answer));
        answer = null;
        Assertions.assertEquals(0, me.evaluate(question, answer));
    }
}
