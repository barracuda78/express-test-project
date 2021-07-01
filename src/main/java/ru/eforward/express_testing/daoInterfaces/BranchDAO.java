package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.school.Branch;
import java.util.List;

public interface BranchDAO {
    /**
     * Adds new branch to school. Used by ADMIN.
     * @param branchName - desired branch name.
     * @param schoolId - id of school to which the branch will be attached.
     * @return  true if branch was added, otherwise returns false
     */
    boolean addBranchByName(String branchName, int schoolId);
    /**
     * Adds new branch to school. Used by ADMIN.
     * @param userId - id of user to which the branches will be attached.
     * @return  List of Branches which particular user is related to.
     * For further development, when one user could be related to several branches.
     */
    List<Branch> getBranchesById(int userId);
}
