package ru.eforward.express_testing.testingProcess;

import ru.eforward.express_testing.testingProcess.evaluatingHandlers.EvaluatingHandler;

public class TestEvaluate implements Evaluating {

    //. Количество баллов за такой ответ может быть равно значению от 0 до 10.
    // В файле с ответами может быть указано несколько вариантов ответа на данный вопрос.
    // Один из них абсолютно правильный, количество баллов при таком ответе равно 10 баллов.
    // Остальные будут оценены в 0 баллов.
    @Override
    public synchronized int getScore(QuestionType type, String question, String choice) {
        EvaluatingHandler evaluatingHandler = QuestionType.getEvaluatingHandler(type);
        return evaluatingHandler.evaluate(question, choice);
    }
}
