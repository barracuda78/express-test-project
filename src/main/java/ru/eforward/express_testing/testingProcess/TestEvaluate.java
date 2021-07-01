package ru.eforward.express_testing.testingProcess;

import ru.eforward.express_testing.testingProcess.evaluatingHandlers.EvaluatingHandler;

public class TestEvaluate implements Evaluating {

/**
* Scores for question diapason: 0-10
* File may contain several variants of answer to the question
* One of them - absolute correct - gives 10 score.
* All the others give 0 score.
* %50% - percentage evaluating - to be implemented in further versions.
*/
    @Override
    public synchronized int getScore(QuestionType type, String question, String choice) {
        EvaluatingHandler evaluatingHandler = QuestionType.getEvaluatingHandler(type);
        return evaluatingHandler.evaluate(question, choice);
    }
}
