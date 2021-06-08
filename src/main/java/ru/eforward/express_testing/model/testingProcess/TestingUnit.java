package ru.eforward.express_testing.model.testingProcess;

import lombok.Getter;
import ru.eforward.express_testing.dao.TestDAOFilesystemImpl;
import ru.eforward.express_testing.daoInterfaces.TestDAO;

@Getter
public class TestingUnit {
    private final int lessonId;
    private final int groupId;
    private String[] questions;
    private int cursor;

    public TestingUnit(int lessonId, int groupId){
        this.lessonId = lessonId;
        this.groupId = groupId;
        init(lessonId);
    }

    private void init(int lessonId){
        TestDAO testDAO = new TestDAOFilesystemImpl();
        String s = testDAO.getTestInfoByLessonId(lessonId);
        //init 'List<String> questions' field here by parsing the given String s in a line above.
        String[] array = s.split("\n\n");
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