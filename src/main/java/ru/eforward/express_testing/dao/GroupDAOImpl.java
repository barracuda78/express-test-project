package ru.eforward.express_testing.dao;

import ru.eforward.express_testing.daoInterfaces.GroupDAO;
import ru.eforward.express_testing.dbConnection.PoolConnector;
import ru.eforward.express_testing.model.school.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAOImpl implements GroupDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;

    @Override
    public List<Integer> getGroupsByTeacherId(int teacherId) {
        List<Integer> groups = new ArrayList<>();
        if(teacherId < 0){
            return groups;
        }
        if(connection == null){
            connection = PoolConnector.getConnection();
        }
        if(connection != null){
            try{
                preparedStatement = connection.prepareStatement("SELECT ID FROM GROUPS WHERE TEACHER_ID = ?");
                preparedStatement.setInt(1, teacherId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.wasNull()){
                    while(resultSet.next()) {
                        int id = resultSet.getInt("ID");
                        groups.add(id);
                    }
                }
                preparedStatement.close();
                PoolConnector.closeConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return groups;
    }
}
