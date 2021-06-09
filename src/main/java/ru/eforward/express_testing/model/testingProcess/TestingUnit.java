package ru.eforward.express_testing.model.testingProcess;

import lombok.Getter;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.Main;
import ru.eforward.express_testing.Slf4jLogbackExampleApp;
import ru.eforward.express_testing.dao.TestDAOFilesystemImpl;
import ru.eforward.express_testing.daoInterfaces.TestDAO;
import ru.eforward.express_testing.utils.LogHelper;

import org.slf4j.Logger;

@Getter
public class TestingUnit {
    private static final Logger logger = LoggerFactory.getLogger(TestingUnit.class);

    private final int lessonId;
    private final int groupId;
    private String[] questions;
    private int cursor;

    public TestingUnit(int lessonId, int groupId){
        this.lessonId = lessonId;
        this.groupId = groupId;
        init(lessonId);
        logger.warn("---Logger: Внимание! Был создан тест!---");
        LogHelper.writeMessage("---LogHelper: Внимание! Был создан тест!---");
    }

    private void init(int lessonId){
        TestDAO testDAO = new TestDAOFilesystemImpl();
        String s = testDAO.getTestInfoByLessonId(lessonId);
        //init 'List<String> questions' field here by parsing the given String s in a line above.
        String[] array = s.split("\n\n");
        logger.info("---Just a log message.");
        logger.debug("---Message for debug level.");
        logger.error("---Failed to read file.");
    }

    public boolean hasNextTest(){
        return questions != null && questions.length >= 1 && cursor < questions.length;
    }

    public String getNextTest(){
        if (questions == null || questions.length < 1 || cursor >= questions.length){
            return null;
        }
        String testString = questions[cursor];
        cursor++;
        return testString;
    }
}