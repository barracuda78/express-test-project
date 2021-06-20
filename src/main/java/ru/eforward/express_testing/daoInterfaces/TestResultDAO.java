package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.testingProcess.TestResult;

import java.util.List;
import java.util.Map;

public interface TestResultDAO {
    boolean addTestResult(TestResult testResult);
    List<TestResult> getTestResultByStudentId(int id);

    List<TestResult> getTestResultsByGroupId(int groupId);

    Map<String, Double> getGroupAverages();

    Map<String, Double> getTeacherGroupAverages(int tId);

    Map<String, Double> getStudentStats(int sId);
}
