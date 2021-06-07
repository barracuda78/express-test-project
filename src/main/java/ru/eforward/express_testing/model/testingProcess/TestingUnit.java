package ru.eforward.express_testing.model.testingProcess;

import ru.eforward.express_testing.dao.LessonDAOImpl;
import ru.eforward.express_testing.dao.TestDAOFilesystemImpl;
import ru.eforward.express_testing.daoInterfaces.LessonDAO;
import ru.eforward.express_testing.daoInterfaces.TestDAO;
import ru.eforward.express_testing.utils.TestFilesReader;

import java.nio.file.Paths;
import java.util.List;

public class TestingUnit {
    int lessonId;

    private List<String> questions;

    public TestingUnit(int lessonId){
        this.lessonId = lessonId;
        init(lessonId);
    }

    public void processTesting(){

    }

    private void init(int lessonId){
        TestDAO testDAO = new TestDAOFilesystemImpl();
        String s = testDAO.getTestInfoByLessonId(lessonId);
        //init 'List<String> questions' field here by parsing the given String s in a line above.
    }
}
