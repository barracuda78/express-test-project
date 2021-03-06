package ru.eforward.express_testing.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.eforward.express_testing.daoInterfaces.BranchDAO;
import ru.eforward.express_testing.dbConnection.PoolConnector;
import ru.eforward.express_testing.model.school.Branch;
import ru.eforward.express_testing.utils.LogHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchDAOImpl implements BranchDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(BranchDAOImpl.class);
    private Connection connection;
    private PreparedStatement preparedStatement;

    @Override
    public boolean addBranchByName(String branchName, int schoolId) {
        LogHelper.writeMessage("class BranchDAOImpl");
        if(branchName == null){
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
                preparedStatement = connection.prepareStatement("INSERT INTO BRANCHES (NAME, SCHOOL_ID) VALUES (?, ?)");
                preparedStatement.setString(1, branchName);
                preparedStatement.setInt(2, schoolId);

                if(!preparedStatement.execute()){
                    updateCount = preparedStatement.getUpdateCount();
                    LogHelper.writeMessage("class BranchDAOImpl, method addBranchByName() : added records to BRANCHES table" + updateCount + "branchName = " + branchName);
                    LOGGER.info("added record to BRANCHES table for school: " + schoolId);
                }
                preparedStatement.close();
                PoolConnector.closeConnection(connection);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                LOGGER.error("SQLException while adding new branch to school: " + schoolId);
            }
        }else{
            LogHelper.writeMessage("class BranchDAOImpl, method addBranchByName() : connection is null");
            LOGGER.warn("method addBranchByName() : connection is null");
        }
        return updateCount > 0;
    }

    @Override
    public List<Branch> getBranchesById(int userId) {
        if(connection == null){
            connection = PoolConnector.getConnection();
        }
        List<Branch> branches = new ArrayList<>();
        if(connection != null) {
            try {
                //???????????????? ???? user_id ??????????, ?? ???? school_id ???????????????? ???????????? branches:
                preparedStatement = connection.prepareStatement("SELECT DISTINCT B.ID, B.NAME FROM USERS " +
                        "INNER JOIN SCHOOLS S on S.ID = USERS.SCHOOL_ID\n" +
                        "INNER JOIN BRANCHES B on S.ID = B.SCHOOL_ID;");
                ResultSet resultSet = preparedStatement.executeQuery();
                if(!resultSet.wasNull()){
                    while(resultSet.next()){
                        int branchId = resultSet.getInt("ID");
                        String branchName = resultSet.getString("NAME");
                        Branch branch = new Branch();
                        branch.setId(branchId);
                        branch.setName(branchName);
                        branches.add(branch);
                    }
                }else{
                    LogHelper.writeMessage("class BranchDAOImpl, method getBranchesById : resultSet is null");
                    LOGGER.warn("resultSet is null");
                }
            } catch (SQLException throwables) {
                LogHelper.writeMessage("class BranchDAOImpl, method getBranchesById : SQLException");
                throwables.printStackTrace();
                LOGGER.error("SQLException while SELECT query - getBranchesById");
            }
        }else{
            LogHelper.writeMessage("class BranchDAOImpl, method getBranchesById : connection is null");
            LOGGER.error("connection is null");
        }
        return branches;
    }
}