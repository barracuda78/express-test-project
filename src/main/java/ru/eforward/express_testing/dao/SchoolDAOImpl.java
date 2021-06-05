package ru.eforward.express_testing.dao;

import ru.eforward.express_testing.daoInterfaces.SchoolDAO;
import ru.eforward.express_testing.dbConnection.PoolConnector;
import ru.eforward.express_testing.model.school.School;
import ru.eforward.express_testing.utils.LogHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchoolDAOImpl implements SchoolDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;

    @Override
    public boolean addSchoolByName(String schoolName) {
        if(schoolName == null){
            return false;
        }

        if(connection == null){
            connection = PoolConnector.getConnection();
        }

        int updateCount = -1;
        if(connection != null){
            try {
                preparedStatement = connection.prepareStatement("INSERT INTO SCHOOLS (NAME) VALUES (?)");
                preparedStatement.setString(1, schoolName);

                if(!preparedStatement.execute()){
                    updateCount = preparedStatement.getUpdateCount();
                    LogHelper.writeMessage("class SchoolDAOImpl, method addSchool() : added records to SCHOOLS table" + updateCount + "schoolName = " + schoolName);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            LogHelper.writeMessage("class SchoolDAOImpl, method addSchool() : connection is null");
        }
        return updateCount > 0;
    }

    @Override
    public boolean addSchool(School school) {
        if(school == null){
            return false;
        }
        if(school.getId() < 0){
            throw new IllegalArgumentException("school.id should be 0 or positive int");
        }

        if(connection == null){
            connection = PoolConnector.getConnection();
        }

        int updateCount = -1;
        if(connection != null){
            try {
                //check if such school with such id already presents in DB. If so - just return from method;
                if(schoolPresents(school.getId())){
                    LogHelper.writeMessage("class SchoolDAOImpl, method addSchool() : schoola already exists in DB. id = " + school.getId());
                    return false;
                }

                preparedStatement = connection.prepareStatement("INSERT INTO SCHOOLS (ID, NAME) VALUES (?, ?)");
                preparedStatement.setInt(1, school.getId());
                preparedStatement.setString(2, school.getName());

                if(!preparedStatement.execute()){
                    updateCount = preparedStatement.getUpdateCount();
                    LogHelper.writeMessage("class SchoolDAOImpl, method addSchool() : added records to SCHOOLS table" + updateCount);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            LogHelper.writeMessage("class SchoolDAOImpl, method addSchool() : connection is null");
        }
        return updateCount > 0;
    }

    @Override
    public boolean schoolPresents(int schoolId){
        if(schoolId < 0 ){
            return false;
        }
        if(connection == null){
            connection = PoolConnector.getConnection();
        }

        if(connection != null){
            try {
                preparedStatement = connection.prepareStatement("SELECT * FROM SCHOOLS WHERE ID = ?");
                preparedStatement.setInt(1, schoolId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet != null){
                    if(resultSet.next()){
                        return true;
                    }
                }else{
                    LogHelper.writeMessage("class SchoolDAOImpl, method schoolPresents() : resultSet is null");
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            LogHelper.writeMessage("class SchoolDAOImpl, method schoolPresents() : connection is null");
        }
        return false;
    }
}
