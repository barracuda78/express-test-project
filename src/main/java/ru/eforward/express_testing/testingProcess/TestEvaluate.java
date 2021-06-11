package ru.eforward.express_testing.testingProcess;

public class TestEvaluate implements Evaluating {

    //. Количество баллов за такой ответ может быть равно значению от 0 до 10.
    // В файле с ответами может быть указано несколько вариантов ответа на данный вопрос.
    // Один из них абсолютно правильный, количество баллов при таком ответе равно 10 баллов.
    // Некоторые из ответов могут быть близки к абсолютно правильному и будут оцениваться от 9 до 1 балла.
    // Остальные, не указанные  в файле с ответами варианты ответов будут оценены в 0 баллов.
    @Override
    public synchronized int evaluateMultiChoice(String choice) {
        return 5;  //todo: implement counting scores
    }

    public synchronized String getCorrectAnswer(){
        return "";
    }

}
