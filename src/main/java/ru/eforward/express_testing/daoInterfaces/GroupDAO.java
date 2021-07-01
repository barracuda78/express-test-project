package ru.eforward.express_testing.daoInterfaces;

import java.util.ArrayList;
import java.util.List;

public interface GroupDAO {
    /**
     * Creates a list of id's of groups which are related to this teacher.
     * @param teacherId - id of a teacher, groups are related to.
     * @return  List of id's of groups which are related to this teacher.
     */
    List<Integer> getGroupIdsByTeacherId(int teacherId);
}
