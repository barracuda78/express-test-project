package ru.eforward.express_testing.testingProcess;

import ru.eforward.express_testing.testingProcess.evaluatingHandlers.EvaluatingHandler;
import ru.eforward.express_testing.testingProcess.evaluatingHandlers.MultiChoiceEvaluator;

import java.util.ArrayList;
import java.util.List;

public class TestEvaluate implements Evaluating {

    //. Количество баллов за такой ответ может быть равно значению от 0 до 10.
    // В файле с ответами может быть указано несколько вариантов ответа на данный вопрос.
    // Один из них абсолютно правильный, количество баллов при таком ответе равно 10 баллов.
    // Некоторые из ответов могут быть близки к абсолютно правильному и будут оцениваться от 9 до 1 балла.
    // Остальные, не указанные  в файле с ответами варианты ответов будут оценены в 0 баллов.
    @Override
    public synchronized int getScore(QuestionType type, String question, String choice) {
        EvaluatingHandler evaluatingHandler = QuestionType.getEvaluatingHandler(type);
        return evaluatingHandler.evaluate(question, choice);
    }


}
