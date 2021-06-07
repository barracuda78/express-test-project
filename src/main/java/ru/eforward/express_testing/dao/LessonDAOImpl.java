package ru.eforward.express_testing.dao;

import ru.eforward.express_testing.daoInterfaces.LessonDAO;
import ru.eforward.express_testing.dbConnection.PoolConnector;
import ru.eforward.express_testing.model.school.Lesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LessonDAOImpl implements LessonDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;

    @Override
    public Lesson getLessonById(int lessonId) {
        if(lessonId <0 ){
            return null;
        }
        Lesson lesson = null;
        if(connection == null){
            connection = PoolConnector.getConnection();
        }
        if(connection != null) {
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM LESSONS WHERE ID = ?");
                preparedStatement.setInt(1, lessonId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.wasNull()){
                    //'while' may be changed to 'if' as only one by lessonId will be found.
                    while(resultSet.next()) {
                        int id = resultSet.getInt("ID");
                        String lessonName = resultSet.getString("LESSON_NAME");
                        String pathToTestFile = resultSet.getString("PATH");
                        int levelId = resultSet.getInt("LEVEL_ID");
                        int courseId = resultSet.getInt("COURSE_ID");
                        int schoolId = resultSet.getInt("SCHOOL_ID");
                        lesson = new Lesson(id, lessonName, pathToTestFile, levelId, courseId, schoolId);
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return lesson;
    }
}
