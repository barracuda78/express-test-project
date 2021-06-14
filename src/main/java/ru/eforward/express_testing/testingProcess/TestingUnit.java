package ru.eforward.express_testing.testingProcess;

import lombok.Getter;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.dao.TestDAOFilesystemImpl;
import ru.eforward.express_testing.daoInterfaces.TestDAO;
import ru.eforward.express_testing.testingProcess.questionHandlers.QuestionHandler;
import ru.eforward.express_testing.utils.LogHelper;

import org.slf4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class TestingUnit implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestingUnit.class);

    private final int lessonId;
    private final int groupId;
    private volatile List<String> questions;
    private volatile int cursor;

    public TestingUnit(int lessonId, int groupId){
        this.lessonId = lessonId;
        this.groupId = groupId;
        init(lessonId);
        LOGGER.info("new test was created for lessonId {} and groupId {}", lessonId, groupId);
    }

    private void init(int lessonId){
        if(lessonId < 1){
            return;
        }
        TestDAO testDAO = new TestDAOFilesystemImpl();
        String s = testDAO.getTestInfoByLessonId(lessonId);
        //init 'List<String> questions' field here by parsing the given String s in a line above.
        String[] array = s.split("\n\n");
        questions = new ArrayList<>(Arrays.asList(array));
    }

    public synchronized boolean hasNextTest(){
        return questions != null && questions.size() >= 1 && cursor < questions.size();
    }

    public synchronized String getNextTest(){
        if (questions == null || questions.size() < 1 || cursor >= questions.size()){
            return null;
        }

        String testString = questions.get(cursor);
        LogHelper.writeMessage("class TestingUnit. getNextTest(). cursor = " + cursor);
        cursor++;
        return questionToHtml(testString);
    }

    private synchronized String questionToHtml(String plainString){
        //here I have to understand witch enum 'QuestionType' is this question.
        //and move this  plainString to appropriate Handler:
        QuestionType questionType = findOutQuestionType(plainString);
        //get handler and pass this questionType to it:
        QuestionHandler questionHandler = QuestionType.getQuestionHandler(questionType);
        return  questionHandler.process(plainString);
    }

    QuestionType findOutQuestionType(String plainString) {
        LogHelper.writeMessage("plainString = " + plainString);
        LogHelper.writeMessage("plainString.split(\"=\").length = " + plainString.split("=").length);
        LogHelper.writeMessage("plainString.toLowerCase() = " + plainString.toLowerCase());

        if(plainString == null || plainString.isEmpty()){
            return QuestionType.UNDEFINED;
        }

        //consider the string has '{', '~' and '=' signs:
        if(plainString.contains("{") && plainString.contains("~") && plainString.contains("=")){
            LogHelper.writeMessage("else if: QuestionType = MULTICHOICE");
            return QuestionType.MULTICHOICE;
        }
        //counting how many '=' signs contains this string (more than 1 ore no one). Also checks if it is not a TRUE_FALSE and other question types.
        else if(((plainString.split("=").length > 2) || (!plainString.contains("=")))
                && plainString.contains("{")
                && !(plainString.toLowerCase().contains("{t}"))
                && !(plainString.toLowerCase().contains("{true}"))
                && !(plainString.contains("->"))
                && !(plainString.contains("{#"))
                && !(plainString.contains("{}"))) {
            LogHelper.writeMessage("else if: QuestionType = SHORT_ANSWER");
            return QuestionType.SHORT_ANSWER;
        }
        //consider the string contains {T} or {TRUE} or {true} or {True} combination:
        else if(plainString.toLowerCase().contains("{t}") || plainString.toLowerCase().contains("{true}") || plainString.toLowerCase().contains("{т}")){
            LogHelper.writeMessage("else if: QuestionType = TRUE_FALSE");
            return QuestionType.TRUE_FALSE;
        }
        else if(plainString.contains("->")){
            LogHelper.writeMessage("else if: QuestionType = COMPLIANCE");
            return QuestionType.COMPLIANCE;
        }
        else if(plainString.contains("{#")){            //&& plainString.matches(".*[0-9]+.*")
            LogHelper.writeMessage("else if: QuestionType = NUMBER_QUESTION");
            return QuestionType.NUMBER;
        }
        else if(plainString.contains("{}")){
            LogHelper.writeMessage("else if: QuestionType = ESSAY");
            return QuestionType.ESSAY;
        }
        else if(!plainString.contains("{}")
                && !plainString.contains("{#")
                && !plainString.contains("->")
                && !plainString.toLowerCase().contains("{t")
                && !isEveryLineCommented(plainString)){
            LogHelper.writeMessage("else if: QuestionType = DESCRIPTION");
            return QuestionType.DESCRIPTION;
        }
        else if(plainString.contains("//")){ //todo: check if in multiline question every line starts with '//'
            LogHelper.writeMessage("else if: QuestionType = COMMENT");
            String[] array = plainString.split("\n");
            AtomicInteger numberOfCommentedStrings = new AtomicInteger(0);
            for(String line : array){
                if(line.startsWith("//")){
                    numberOfCommentedStrings.incrementAndGet();
                }
            }
            return array.length == numberOfCommentedStrings.get() ? QuestionType.COMMENT : QuestionType.UNDEFINED;
        }

        LogHelper.writeMessage("---class TestingUnit method findOut(): Невозможно определить тип вопроса! QuestionType.UNDEFINED");
        LOGGER.error("Невозможно определить тип вопроса! QuestionType.UNDEFINED");
        return QuestionType.UNDEFINED;
    }

    private boolean isEveryLineCommented(String q){
        String[] array = q.split("\n");
        AtomicInteger numberOfCommentedStrings = new AtomicInteger(0);
        for(String line : array){
            if(line.startsWith("//")){
                numberOfCommentedStrings.incrementAndGet();
            }
        }
        return array.length == numberOfCommentedStrings.get();
    }
}