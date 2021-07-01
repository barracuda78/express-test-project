package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.school.Lesson;

import java.util.List;

public interface LessonDAO {
    /**
     * Returns a lesson from DB by particular id.
     * @param lessonId - id of a desired lesson.
     * @return  Lesson from DB, which is identified by certain id;
     */
    Lesson getLessonById(int lessonId);

    /**
     * Returns a list of lessons from DB existing in the school.
     * @return  all the Lessons from the school;
     */
    List<Lesson> getAllLessons();
}
