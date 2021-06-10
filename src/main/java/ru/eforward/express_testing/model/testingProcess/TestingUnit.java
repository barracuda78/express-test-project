package ru.eforward.express_testing.model.testingProcess;

import lombok.Getter;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.dao.TestDAOFilesystemImpl;
import ru.eforward.express_testing.daoInterfaces.TestDAO;
import ru.eforward.express_testing.model.testingProcess.enumHandlers.QuestionHandler;
import ru.eforward.express_testing.utils.LogHelper;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class TestingUnit {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestingUnit.class);

    private final int lessonId;
    private final int groupId;
    private volatile List<String> questions;
    private volatile int cursor;

    public TestingUnit(int lessonId, int groupId){
        this.lessonId = lessonId;
        this.groupId = groupId;
        init(lessonId);
        LOGGER.warn("new test was created for lessonId {} and groupId {}", lessonId, groupId);
        LogHelper.writeMessage("---class TestingUnit method init() : Внимание! Был создан тест!---");
    }

    private void init(int lessonId){
        TestDAO testDAO = new TestDAOFilesystemImpl();
        String s = testDAO.getTestInfoByLessonId(lessonId);
        //init 'List<String> questions' field here by parsing the given String s in a line above.
        String[] array = s.split("\n\n");
//        logger.info("---Just a log message.");
//        logger.debug("---Message for debug level.");
//        logger.error("---Failed to read file.");
        questions = new ArrayList<>(Arrays.asList(array));
        //LogHelper.writeMessage("---class TestingUnit method init() : questions = " + questions);
    }

    public synchronized boolean hasNextTest(){
        return questions != null && questions.size() >= 1 && cursor < questions.size();
    }

    public synchronized String getNextTest(){
        if (questions == null || questions.size() < 1 || cursor >= questions.size()){
            return null;
        }
//        String testString = questions.get(cursor);
//        if(testString.startsWith("//")){
//            cursor++;
//            getNextTest();
//        }
//        cursor++;
        return questionToHtml(questions.get(3)); //todo: remove hardcoded '3' with appropriate logic.
    }

    private synchronized String questionToHtml(String plainString){
        //here I have to understand witch enum 'QuestionType' is this question.
        //and move this  plainString to appropriate Handler:
        QuestionType questionType = findOutQuestionType(plainString);
        //get handler and pass this questionType to it:
        QuestionHandler questionHandler = QuestionType.getHandler(questionType);
        return  questionHandler.process(plainString);
    }

    private QuestionType findOutQuestionType(String plainString) {
        //todo: implement logic instead of hardcode:
        return QuestionType.MULTICHOICE;
    }

}