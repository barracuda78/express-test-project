package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.testingProcess.TestResult;

import java.util.List;

public interface TestResultDAO {
    boolean addTestResult(TestResult testResult);
    List<TestResult> getTestResultByStudentId(int id);

    List<TestResult> getTestResultsByGroupId(int groupId);

}
