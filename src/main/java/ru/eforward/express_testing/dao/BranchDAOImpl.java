package ru.eforward.express_testing.dao;

import ru.eforward.express_testing.daoInterfaces.BranchDAO;
import ru.eforward.express_testing.dbConnection.PoolConnector;
import ru.eforward.express_testing.utils.LogHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BranchDAOImpl implements BranchDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;

    @Override
    public boolean addBranchByName(String branchName) {
        if(branchName == null){
            return false;
        }
        if(connection == null){
            connection = PoolConnector.getConnection();
        }

        int updateCount = -1;
        if(connection != null){
            try {
                //здесь нужно вычислить id школы и передавать его в БД внешним ключом.
                //id школы можно вычислить из данных пользователя. - branch_id - по нему вытаскивать school_id
                preparedStatement = connection.prepareStatement("INSERT INTO BRANCHES (NAME) VALUES (?)");
                preparedStatement.setString(1, branchName);

                if(!preparedStatement.execute()){
                    updateCount = preparedStatement.getUpdateCount();
                    LogHelper.writeMessage("class BranchDAOImpl, method addBranchByName() : added records to BRANCHES table" + updateCount + "branchName = " + branchName);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            LogHelper.writeMessage("class BranchDAOImpl, method addBranchByName() : connection is null");
        }
        return updateCount > 0;
    }
}