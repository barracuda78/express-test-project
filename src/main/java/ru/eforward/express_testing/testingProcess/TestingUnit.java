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

/**
 * This is a main class for testing process which is used as:
 * - a storage of questions for testing,
 * - a storage of answers for testing,
 * - an iterator for questions.
 */

@Getter
public class TestingUnit implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestingUnit.class);

    private final int lessonId;
    private final int groupId;
    private final double duration; //for example 3.5 minutes
    private List<String> questions;
    private int cursor;


    public TestingUnit(int lessonId, int groupId, double duration){
        this.lessonId = lessonId;
        this.groupId = groupId;
        this.duration = duration;
        init(lessonId);
        LOGGER.info("new test was created for lessonId {} and groupId {}", lessonId, groupId);
    }

    /**
     * Is used in constructor. Initiates field questions with the list of Questions from Storage.
     * @param lessonId - the id of a lesson for which Testing will be started.
     */
    private void init(int lessonId){
        if(lessonId < 1){
            return;
        }
        TestDAO testDAO = new TestDAOFilesystemImpl();
        String s = testDAO.getTestInfoByLessonId(lessonId);
        //init 'List<String> questions' field here by parsing the given String s in a line above.
        String[] array = s.split("\n\n");
        questions = new ArrayList<>(Arrays.asList(array));
        LogHelper.writeMessage("TestingUnit :: questions = \n" + questions);
    }

    /**
     * Determines if it is the end of questions list or not
     * @return true if not all questions from list were answered, otherwise returns false.
     */
    public boolean hasNextTest(){
        return questions != null && questions.size() >= 1 && cursor < questions.size();
    }

    /**
     * Iterates forward by the questions field of this class
     * @return next String representation of a question from questions List.
     */
    public String getNextTest(){
        if (questions == null || questions.size() < 1 || cursor >= questions.size()){
            return null;
        }

        String testString = questions.get(cursor);
        LogHelper.writeMessage("class TestingUnit. getNextTest(). cursor = " + cursor);
        cursor++;
        return questionToHtml(testString);
    }

    /**
     * Creates an HTML-formatted string from a plain question String
     * @param plainString - String representation of a question
     * @return an HTML-formatted string from a plain question String
     */
    private String questionToHtml(String plainString){
        //here I have to understand witch enum 'QuestionType' is this question.
        //and move this  plainString to appropriate Handler:
        QuestionType questionType = findOutQuestionType(plainString);
        //get handler and pass this questionType to it:
        QuestionHandler questionHandler = QuestionType.getQuestionHandler(questionType);
        return  questionHandler.process(plainString);
    }

    /**
     * Determines what type is a question in terms of GIFT-convention by given String representation of a question.
     * @param plainString - given String representation of a question.
     * @return a QuestionType of a given String representation of a question or UNDEFINED if not a GIGT format.
     */
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

    /**
     * Checks if this question is actually not a question but just a comment.
     * @param q - a string representation of a question.
     * @return true if it is just a comment and not a question, otherwise returns false.
     */
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

    /**
    * This method is used in AnswerTestingServlet in case of any exceptions
     * (such as NumberFormatException or IllegalArgumentException) when trying to evaluate
     * a NumberQuestion. Just need to make student understand than wrong number format was entered.
     * Decrements cursor to give chance answer this question correctly.
    * */
    public void decrementCursor(){
        cursor--;
    }

    /**
     * Can determine the cursor position. For example for setting it to the end of questions
     * to make test off.
     * @param size - the desired position of cursor.
     */
    public void setCursor(int size) {
        cursor = size;
    }
}