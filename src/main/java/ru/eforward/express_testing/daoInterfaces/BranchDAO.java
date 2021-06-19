package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.school.Branch;

import java.util.List;

public interface BranchDAO {
    boolean addBranchByName(String branchName, int schoolId);

    List<Branch> getBranchesById(int userId);
}
