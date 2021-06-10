package ru.eforward.express_testing.model.testingProcess;

import ru.eforward.express_testing.model.testingProcess.enumHandlers.DefaultHandler;
import ru.eforward.express_testing.model.testingProcess.enumHandlers.MultichoiceHandler;
import ru.eforward.express_testing.model.testingProcess.enumHandlers.QuestionHandler;

import java.util.ArrayList;
import java.util.List;

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
    //Непонятно какой тип вороса:
    UNDEFINED;

    public static QuestionHandler getHandler(QuestionType enumType){
        List<QuestionHandler> handlers = new ArrayList<>();
        //todo: implement package scanning here for automatic adding of entities from package enumHandlers.
        handlers.add(new MultichoiceHandler());
        handlers.add(new DefaultHandler());

        for(QuestionHandler h : handlers){
            if(h.canProcess(enumType)){
                return h;
            }
        }
        return new DefaultHandler();
    }
}
