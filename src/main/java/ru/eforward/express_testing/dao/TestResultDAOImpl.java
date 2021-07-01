package ru.eforward.express_testing.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.daoInterfaces.TestResultDAO;
import ru.eforward.express_testing.dbConnection.PoolConnector;
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

    @Override //TO GET STATS BY STUDENT FOR ALL HIS TESTRESULTS. LESSON_NAME - AVERAGE_SCORE.
    public Map<String, Double> getStudentStats(int sId){
        if(sId <= 0){
            return null;
        }
        try {
            if(connection == null || connection.isClosed()){
                connection = PoolConnector.getConnection();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.error("jdbc connection problem");
        }
        Map<String, Double> map = null;
        if(connection != null) {
            try {
                String query = "SELECT LESSON_NAME, AVG(TOTAL_SCORE) AS AVERAGE, USERS.ID FROM USERS\n" +
                        "INNER JOIN TESTRESULTS T on USERS.ID = T.STUDENT_ID\n" +
                        "INNER JOIN LESSONS L on T.LESSON_ID = L.ID\n" +
                        "GROUP BY LESSON_NAME, USERS.ID HAVING USERS.ID = ?;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, sId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.wasNull()){
                    while(resultSet.next()){
                        map = new LinkedHashMap<>();
                        String lesson_name = resultSet.getString("LESSON_NAME");
                        Double total_score = resultSet.getDouble("AVERAGE");
                        map.put(lesson_name, total_score);
                    }
                }
                preparedStatement.close();
                PoolConnector.closeConnection(connection);
            } catch (SQLException throwables) {
                LOGGER.error("SQLException while select query to DB getting student stats");
                throwables.printStackTrace();
            }
        }
        LogHelper.writeMessage("TestResultDAOImpl: getStudentStats(): map = " + map);
        return map;
    }

    @Override
    public Map<String, Double> getTeacherGroupAverages(int tId){
        if(tId <= 0){
            return null;
        }
        try {
            if(connection == null || connection.isClosed()){
                connection = PoolConnector.getConnection();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.error("jdbc connection problem");
        }
        Map<String, Double> map = null;
        if(connection != null) {
            try {
                String query = "SELECT GROUP_NAME, AVG(TOTAL_SCORE) AS AVERAGE, G.TEACHER_ID  FROM USERS\n" +
                        "INNER JOIN GROUPS G on USERS.GROUP_ID = G.ID\n" +
                        "INNER JOIN TESTRESULTS T on USERS.ID = T.STUDENT_ID\n" +
                        "GROUP BY GROUP_NAME, G.TEACHER_ID HAVING G.TEACHER_ID = ?;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, tId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.wasNull()){
                    map = new LinkedHashMap<>();
                    while(resultSet.next()) {
                        String groupName = resultSet.getString("GROUP_NAME");
                        Double averageScores = resultSet.getDouble("AVERAGE");
                        map.put(groupName, averageScores);
                    }
                }
                preparedStatement.close();
                PoolConnector.closeConnection(connection);
            } catch (SQLException throwables) {
                LOGGER.error("SQLException while select query to DB getting group averages stats");
                throwables.printStackTrace();
            }
        }
        return map;
    }

    @Override
    public Map<String, Double> getGroupAverages(){
        try {
            if(connection == null || connection.isClosed()){
                connection = PoolConnector.getConnection();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.error("jdbc connection problem");
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
        return map;
    }

    @Override
    public List<TestResult> getTestResultsByGroupId(int groupId){
        if(groupId <= 0){
            return null;
        }
        try {
            if(connection == null || connection.isClosed()){
                connection = PoolConnector.getConnection();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.error("jdbc connection problem");
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
        try {
            if(connection == null || connection.isClosed()){
                connection = PoolConnector.getConnection();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.error("jdbc connection problem");
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
        try {
            if(connection == null || connection.isClosed()){
                connection = PoolConnector.getConnection();
            }
        } catch (SQLException throwables) {
            LOGGER.error("jdbc connection problem");
            throwables.printStackTrace();
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

    /**
     * Creates a mapping 'question - answer' of plain String stored within a given parameter results
     * pair-method createStringFromMap()
     */
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

    /**
     * Creates a plain String from mapping 'question - answer'
     * pair method createMapFromString()
     */
    private String createStringFromMap(Map<String, String> map) {
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
