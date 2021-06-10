package ru.eforward.express_testing.model.testingProcess;

import lombok.Getter;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.dao.TestDAOFilesystemImpl;
import ru.eforward.express_testing.daoInterfaces.TestDAO;
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
        LogHelper.writeMessage("---class TestingUnit method init() : questions = " + questions);
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
        return covertPlainStringToValidHtmlString(questions.get(3));
    }

    //        <form method="post" action="">
    //            <input type="text" required placeholder="login" name="login"><br>
    //            <input type="password" required placeholder="password" name="password"><br><br>
    //            <input class="button" type="submit" value="Войти">
    //
    //        </form>
    private synchronized String covertPlainStringToValidHtmlString(String plainString){
        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
        sb.append("<br>");
        sb.append(buildNiceQuestionFromPlainQuestion(plainString));   //------>this is the question itself;
        sb.append("<br>");
        sb.append("<form method=\"get\" action=\"AnswerHandlerServlet\">");  //TODO:------------->have to create this servlet
        sb.append("<input type=\"text\" required placeholder=\"type your answer\" name=\"answer\"><br>");
        sb.append("<input class=\"button\" type=\"submit\" name=\"answeredButton\" value=\"Отправить\">");
        sb.append("</form>");
        sb.append("</p>");

        return sb.toString();
    }

    //Grant is {~buried =entombed ~living ~затрудняюсь ответить} in Grant's tomb.
    private synchronized String buildNiceQuestionFromPlainQuestion(String q){
        LogHelper.writeMessage("---class TestingUnit method  buildNice...() q = " + q);
        StringBuilder sb = new StringBuilder();
        String allVariants = q.substring(q.indexOf('{') + 1, q.indexOf('}'));
        String questionItself = q.substring(0, q.indexOf('{')) + q.substring(q.indexOf('}') + 1);

        sb.append("<b>");
        sb.append(questionItself);
        sb.append("</b>");
        sb.append("<ul>");
        for(String variant : allVariants.split("[\\s]+")){
            sb.append("<li>");
            sb.append(variant);
            sb.append("</li>");
        }
        sb.append("</ul>");

        return sb.toString();
    }
}