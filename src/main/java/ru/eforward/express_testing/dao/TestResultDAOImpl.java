package ru.eforward.express_testing.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.daoInterfaces.TestResultDAO;
import ru.eforward.express_testing.dbConnection.PoolConnector;
import ru.eforward.express_testing.model.school.Lesson;
import ru.eforward.express_testing.testingProcess.TestResult;
import ru.eforward.express_testing.utils.LogHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestResultDAOImpl implements TestResultDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestResultDAOImpl.class);
    private Connection connection;
    private PreparedStatement preparedStatement;

    @Override
    public Map<String, Double> getGroupAverages(){
        if(connection == null){
            connection = PoolConnector.getConnection();
        }
        Map<String, Double> map = null;
        if(connection != null) {
            try {
                String query = "SELECT GROUP_NAME, AVG(TOTAL_SCORE) AS AVERAGE FROM USERS\n" +
                        "INNER JOIN GROUPS G on USERS.GROUP_ID = G.ID\n" +
                        "INNER JOIN TESTRESULTS T on USERS.ID = T.STUDENT_ID\n" +
                        "GROUP BY GROUP_NAME;";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if(!resultSet.wasNull()){
                    map = new LinkedHashMap<>();
                    while(resultSet.next()) {
                        String groupName = resultSet.getString("GROUP_NAME");
                        Double averageScores = resultSet.getDouble("AVERAGE");
                        map.put(groupName, averageScores);
                    }
                }
                statement.close();
                PoolConnector.closeConnection(connection);
            } catch (SQLException throwables) {
                LOGGER.error("SQLException while select query to DB getting group averages stats");
                throwables.printStackTrace();
            }
        }
        LogHelper.writeMessage("TestResultDAOImpl : map = " + map);
        return map;

    }

    @Override
    public List<TestResult> getTestResultsByGroupId(int groupId){
        if(groupId <= 0){
            return null;
        }
        if(connection == null){
            connection = PoolConnector.getConnection();
        }
        List<TestResult> testResults = null;
        if(connection != null) {
            try {
                preparedStatement = connection.prepareStatement("SELECT USERS.ID, USERS.LASTNAME, T.ID, LESSON_ID, RESULTS, TOTAL_SCORE\n" +
                        "    FROM USERS INNER JOIN GROUPS G on G.ID = USERS.GROUP_ID\n" +
                        "        INNER JOIN TESTRESULTS T on USERS.ID = T.STUDENT_ID\n" +
                        "            WHERE G.ID = ?;");
                preparedStatement.setInt(1, groupId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.wasNull()){
                    testResults = new ArrayList<>();
                    while(resultSet.next()) {
                        //SELECT USERS.ID, USERS.LASTNAME, T.ID, LESSON_ID, RESULTS, TOTAL_SCORE - from resultset

                        //public class TestResult {
                        //    private int id;
                        //    private int studentId;
                        //    private int schoolId;
                        //    private int lessonId;
                        //    private Map<String, String> map = new LinkedHashMap<>();
                        //    private int totalScore;
                        int id = resultSet.getInt("TESTRESULTS.ID");
                        int studentId = resultSet.getInt("USERS.ID");
                        int schoolId = 1; //todo: do not hardcode it, build one more join to SCHOOLS table;
                        int lessonId = resultSet.getInt("LESSON_ID");
                        String results = resultSet.getString("RESULTS");
                        int totalScore = resultSet.getInt("TOTAL_SCORE");

                        TestResult testResult = new TestResult();
                        testResult.setId(id);
                        testResult.setStudentId(studentId);
                        testResult.setSchoolId(schoolId);
                        testResult.setLessonId(lessonId);
                        testResult.setMap(createMapFromString(results));
                        testResult.setTotalScore(totalScore);

                        testResults.add(testResult);
                    }
                }
                preparedStatement.close();
                PoolConnector.closeConnection(connection);
            } catch (SQLException throwables) {
                LOGGER.error("SQLException while select query to DB getting group stats");
                throwables.printStackTrace();
            }
        }
        return testResults;
    }


    @Override
    public boolean addTestResult(TestResult testResult) {
        if(testResult == null){
            return false;
        }
        if(connection == null){
            connection = PoolConnector.getConnection();
        }
        int updateCount = -1;
        if(connection != null){
            try {
                //do not have id yet - will de generated by DB.
                int studentId = testResult.getStudentId();
                LogHelper.writeMessage("TestResultDAO ---> studentId = " + studentId);
                int lessonId = testResult.getLessonId();
                String results = createStringFromMap(testResult.getMap());
                int totalScore = testResult.getTotalScore();
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO TESTRESULTS (STUDENT_ID, LESSON_ID, RESULTS, TOTAL_SCORE) VALUES (?, ?, ?, ?)");
                preparedStatement.setInt(1, studentId);
                preparedStatement.setInt(2, lessonId);
                preparedStatement.setString(3, results);
                preparedStatement.setInt(4, totalScore);

                if(!preparedStatement.execute()){
                    updateCount = preparedStatement.getUpdateCount();
                    LogHelper.writeMessage("class TestResultDAOImpl, method addTestResult : added record to TESTRESULTS table");
                    LOGGER.info("New  record to TESTRESULTS table has been added by student with ID " + studentId);
                }

                preparedStatement.close();
                PoolConnector.closeConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                LOGGER.error("Can not add record to TESTRESULTS table");
            }
        }else{
            LogHelper.writeMessage("class TestResultDAOImpl, method addTestResult : connection is null");
            LOGGER.error("method addTestResult : connection is null");
        }
        return updateCount > 0;
    }

    @Override
    public List<TestResult> getTestResultByStudentId(int studentId) {
        if(studentId < 0){
            return null;
        }
        if(connection == null){
            connection = PoolConnector.getConnection();
        }
        List<TestResult> testResults = null;
        if(connection != null) {
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM TESTRESULTS WHERE STUDENT_ID = ?");
                preparedStatement.setInt(1, studentId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.wasNull()){
                    testResults = new ArrayList<>();
                    while(resultSet.next()) {
                        int id = resultSet.getInt("ID");
                        //int studentId - we have it from method parameter
                        int lessonId = resultSet.getInt("LESSON_ID");
                        String results = resultSet.getString("RESULTS");
                        int totalScore = resultSet.getInt("TOTAL_SCORE");

                        TestResult testResult = new TestResult();
                        testResult.setId(id);
                        testResult.setStudentId(studentId);
                        testResult.setLessonId(lessonId);
                        testResult.setMap(createMapFromString(results));
                        testResult.setTotalScore(totalScore);

                        testResults.add(testResult);
                    }
                }
                preparedStatement.close();
                PoolConnector.closeConnection(connection);
            } catch (SQLException throwables) {
                LOGGER.error("SQLException while select query to DB getting students stats");
                throwables.printStackTrace();
            }
        }
        return testResults;
    }

    private Map<String, String> createMapFromString(String results) {
        Map<String, String> map = new LinkedHashMap<>();
        String[] pairs = results.split("==\\$\\$==");
        for(String pair : pairs){
            String key = pair.substring(0, pair.indexOf("==$=="));
            String value = pair.substring(pair.indexOf("==$=="));
            map.put(key, value);
        }
        return map;
    }

    private synchronized String createStringFromMap(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String, String> pair : map.entrySet()){
            String key = pair.getKey();
            String value = pair.getValue();
            sb.append(key);
            sb.append("==$=="); //between question and answer.
            sb.append(value);
            sb.append("==$$=="); //after every pair.
        }
        return sb.toString();
    }
}
