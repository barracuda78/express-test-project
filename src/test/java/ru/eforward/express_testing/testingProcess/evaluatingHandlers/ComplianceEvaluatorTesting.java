package ru.eforward.express_testing.testingProcess.evaluatingHandlers;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.eforward.express_testing.testingProcess.QuestionType;

public class ComplianceEvaluatorTesting {
    @Test
    public void canEvaluateTesting(){
        ComplianceEvaluator ce = new ComplianceEvaluator();

        Assertions.assertEquals(false,
                ce.canEvaluate(QuestionType.MULTICHOICE),
                "Should return false with MULTICHOICE type as a parameter");

        Assertions.assertFalse(ce.canEvaluate(QuestionType.SHORT_ANSWER),
                "Should return false with SHORT_ANSWER type as a parameter");

        Assertions.assertTrue(ce.canEvaluate(QuestionType.COMPLIANCE),
                "Should return true with COMPLIANCE type as a parameter");

        Assertions.assertFalse(ce.canEvaluate(QuestionType.DESCRIPTION),
                "Should return false with DESCRIPTION type as a parameter");

        Assertions.assertFalse(ce.canEvaluate(QuestionType.TRUE_FALSE),
                "Should return false with TRUE_FALSE type as a parameter");

        Assertions.assertFalse(ce.canEvaluate(QuestionType.ESSAY),
                "Should return false with ESSAY type as a parameter");

        Assertions.assertFalse(ce.canEvaluate(QuestionType.NUMBER),
                "Should return false with NUMBER type as a parameter");

        Assertions.assertFalse(ce.canEvaluate(QuestionType.COMMENT),
                "Should return false with COMMENT type as a parameter");

        Assertions.assertFalse(ce.canEvaluate(QuestionType.UNDEFINED),
                "Should return false with UNDEFINED type as a parameter");
    }

    @Test
    public void evaluateTest(){
        ComplianceEvaluator ce = new ComplianceEvaluator();
        String question = "Укажите столицы государств: {\n" +
                "    =Канада -> Оттава\n" +
                "    =Италия -> Рим\n" +
                "    =Япония -> Токио\n" +
                "    =Индия  -> Нью Дели\n" +
                "}";

        String answer = "Мадагаскар";
        Assertions.assertEquals(0, ce.evaluate(question, answer));
    }
}