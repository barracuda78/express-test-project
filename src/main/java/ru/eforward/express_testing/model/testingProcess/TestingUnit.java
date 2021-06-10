package ru.eforward.express_testing.model.testingProcess;

import lombok.Getter;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.dao.TestDAOFilesystemImpl;
import ru.eforward.express_testing.daoInterfaces.TestDAO;
import ru.eforward.express_testing.utils.LogHelper;

import org.slf4j.Logger;

@Getter
public class TestingUnit {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestingUnit.class);

    private final int lessonId;
    private final int groupId;
    private volatile String[] questions;
    private volatile int cursor;

    public TestingUnit(int lessonId, int groupId){
        this.lessonId = lessonId;
        this.groupId = groupId;
        init(lessonId);
        LOGGER.warn("new test was created for lessonId {} and groupId {}", lessonId, groupId);
        LogHelper.writeMessage("---LogHelper: Внимание! Был создан тест!---");
    }

    private void init(int lessonId){
        TestDAO testDAO = new TestDAOFilesystemImpl();
        String s = testDAO.getTestInfoByLessonId(lessonId);
        //init 'List<String> questions' field here by parsing the given String s in a line above.
        String[] array = s.split("\n\n");
//        logger.info("---Just a log message.");
//        logger.debug("---Message for debug level.");
//        logger.error("---Failed to read file.");
        questions = new String[10];
        questions[0] = array[0];
        questions[1] = "fdsfdsfds";
        questions[2] = "блаблабла2";
        questions[3] = "блаблабла3";
        questions[4] = "блаблабла4";
        questions[5] = "блаблабла5";
        questions[6] = "блаблабла6";
        questions[7] = "блаблабла7";
        questions[8] = "блаблабла8";
        questions[9] = "блаблабла9";
    }

    public synchronized boolean hasNextTest(){
        return questions != null && questions.length >= 1 && cursor < questions.length;
    }

    public synchronized String getNextTest(){
        if (questions == null || questions.length < 1 || cursor >= questions.length){
            return null;
        }
        String testString = questions[cursor];
        cursor++;
        return covertPlainStringToValidHtmlString(testString);
    }

    //        <form method="post" action="">
    //
    //            <input type="text" required placeholder="login" name="login"><br>
    //            <input type="password" required placeholder="password" name="password"><br><br>
    //            <input class="button" type="submit" value="Войти">
    //
    //        </form>
    private synchronized String covertPlainStringToValidHtmlString(String plainString){
        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
        sb.append("<br>");
        sb.append(plainString);   //------>this is the question itself;
        sb.append("<br>");
        sb.append("<form method=\"get\" action=\"AnswerHandlerServlet\">");  //TODO:------------->have to create this servlet
        sb.append("<input type=\"text\" required placeholder=\"type your answer\" name=\"answer\"><br>");
        sb.append("<input class=\"button\" type=\"submit\" name=\"answeredButton\" value=\"Отправить\">");
        sb.append("</form>");
        sb.append("</p>");

        return sb.toString();
    }
}