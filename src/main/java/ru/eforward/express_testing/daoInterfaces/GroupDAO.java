package ru.eforward.express_testing.daoInterfaces;

import java.util.ArrayList;
import java.util.List;

public interface GroupDAO {
    List<Integer> getGroupIdsByTeacherId(int teacherId);
}
