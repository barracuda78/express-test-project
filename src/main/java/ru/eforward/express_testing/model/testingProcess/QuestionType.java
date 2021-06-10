package ru.eforward.express_testing.model.testingProcess;

//about .gift file format and types of question:
//https://www.masu.edu.ru/moodle/help.php?file=formatgift.html&module=quiz
public enum QuestionType {
    //Множественный выбор:
    MULTICHOICE,
    //Короткий ответ:
    SHORT_ANSWER,
    //Верно/неверно:
    TRUE_FALSE,
    //Вопрос на соответствие:
    COMPLIANCE,
    //Числовой вопрос:
    NUMBER_QUESTION,
    //Эссэ:
    ESSAY,
    //Описание:
    DESCRIPTION,
    //Строка комментария:
    COMMENT,
    //Название вопроса:
    QUESTION_NAME,
    //Комментарий на вариант ответа:
    ANSWER_COMMENT,


}
