package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.school.Test;

public interface TestDAO {
    String getTestInfoByLessonId(int lessonId);
}
