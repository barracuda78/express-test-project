package ru.eforward.express_testing.dao;

import ru.eforward.express_testing.daoInterfaces.LessonDAO;
import ru.eforward.express_testing.daoInterfaces.TestDAO;
import ru.eforward.express_testing.utils.TestFilesReader;

import java.nio.file.Paths;

public class TestDAOFilesystemImpl implements TestDAO {
    private TestFilesReader testFilesReader;
    @Override
    public String getTestInfoByLessonId(int lessonId){
        //1. get lesson from db by lessonId
        //2. get path from lesson
        //3. getFileInfo from the path.
        testFilesReader = new TestFilesReader();
        LessonDAO lessonDAO = new LessonDAOImpl();
        String path = lessonDAO.getLessonById(lessonId).getPathToTestFile();
        return testFilesReader.readTestFile(Paths.get(path));
    }
}
