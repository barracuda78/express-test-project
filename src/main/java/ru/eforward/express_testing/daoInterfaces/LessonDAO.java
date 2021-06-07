package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.school.Lesson;

public interface LessonDAO {
    Lesson getLessonById(int lessonId);
}
