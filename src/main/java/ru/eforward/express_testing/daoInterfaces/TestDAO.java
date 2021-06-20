package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.school.Test;

public interface TestDAO {
    /**
     * Returns String representation of Path to the Test.
     * @param lessonId - id of a lesson to create test on.
     * @return  String representation of Path to the Test.
     */
    String getTestInfoByLessonId(int lessonId);
}
