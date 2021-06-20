package ru.eforward.express_testing.dao;

import ru.eforward.express_testing.daoInterfaces.LessonDAO;
import ru.eforward.express_testing.daoInterfaces.TestDAO;
import ru.eforward.express_testing.utils.TestFilesReader;

import java.nio.file.Paths;
/**
 * Class is used for development purposes only when not connected to DB.
 */
public class TestDAOFilesystemImpl implements TestDAO {
    private TestFilesReader testFilesReader;


    @Override
    public String getTestInfoByLessonId(int lessonId){
        testFilesReader = new TestFilesReader();
        LessonDAO lessonDAO = new LessonDAOImpl();
        String path = lessonDAO.getLessonById(lessonId).getPathToTestFile();
        return testFilesReader.readTestFile(Paths.get(path));
    }
}
