package ru.eforward.express_testing.testingProcess;

import ru.eforward.express_testing.testingProcess.evaluatingHandlers.*;
import ru.eforward.express_testing.testingProcess.questionHandlers.*;


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
    NUMBER,
    //Эссэ:
    ESSAY,
    //Описание:
    DESCRIPTION,
    //Строка комментария:
    COMMENT,
    //Непонятно какой тип вороса:
    UNDEFINED;

    public static QuestionHandler getQuestionHandler(QuestionType enumType){
        List<QuestionHandler> handlers = new ArrayList<>();
        //todo: implement package scanning here for automatic adding of entities from package enumHandlers.
        handlers.add(new MultichoiceHandler());
        handlers.add(new ShortAnswerHandler());
        handlers.add(new TrueFalseHandler());
        handlers.add(new ComplianceHandler());
        handlers.add(new NumberHandler());
        handlers.add(new EssayHandler());
        handlers.add(new DescriptionHandler());
        handlers.add(new CommentHandler());
        handlers.add(new UndefinedHandler());

        for(QuestionHandler h : handlers){
            if(h.canProcess(enumType)){
                return h;
            }
        }

        return new UndefinedHandler();
    }

    public static EvaluatingHandler getEvaluatingHandler(QuestionType enumType){
        List<EvaluatingHandler> handlers = new ArrayList<>();
        //todo: implement package scanning here for automatic adding of entities from package evaluatingHandlers.
        handlers.add(new MultiсhoiceEvaluator());
        handlers.add(new ShortAnswerEvaluator());
        handlers.add(new TrueFalseEvaluator());
        handlers.add(new ComplianceEvaluator());

        for(EvaluatingHandler h : handlers){
            if(h.canEvaluate(enumType)){
                return h;
            }
        }
        return new UndefinedEvaluator();
    }
}
