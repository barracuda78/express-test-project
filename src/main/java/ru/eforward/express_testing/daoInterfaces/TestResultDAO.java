package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.testingProcess.TestResult;

import java.util.List;
import java.util.Map;

public interface TestResultDAO {
    /**
     * Adds a given TestResult to the storage.
     * @param testResult - TestResult to be added to Storage.
     * @return  true if TestResult was added successfuly, otherwise returns false.
     */
    boolean addTestResult(TestResult testResult);

    /**
     * Creates a list of TestResults fom the storage for particular user.
     * @param id - id of a student for getting statistics by.
     * @return  list of TestResults fom the storage for particular user.
     */
    List<TestResult> getTestResultByStudentId(int id);

    /**
     * Creates a list of TestResults fom the storage for particular group.
     * @param groupId - id of a group for getting statistics by.
     * @return  list of TestResults fom the storage for particular group.
     */
    List<TestResult> getTestResultsByGroupId(int groupId);

    /**
     * Creates a map of TestResults fom the storage for whole school.
     * @return  map of TestResults fom the storage for particular group as LESSON_NAME : AVERAGE_SCORES
     */
    Map<String, Double> getGroupAverages();

    /**
     * Creates a map of TestResults fom the storage for particular teacher (groups attached to this teacher).
     * @param tId - id of a teacher for building the stats info.
     * @return  map of TestResults fom the storage for particular group as LESSON_NAME : AVERAGE_SCORES
     */
    Map<String, Double> getTeacherGroupAverages(int tId);

    /**
     * Creates a map of TestResults fom the storage for particular student.
     * @param sId - id of a student for building the stats info.
     * @return  map of TestResults fom the storage for particular student as LESSON_NAME : AVERAGE_SCORES
     */
    Map<String, Double> getStudentStats(int sId);
}
