package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.school.Lesson;

import java.util.List;

public interface LessonDAO {
    Lesson getLessonById(int lessonId);
    List<Lesson> getAllLessons();
}
