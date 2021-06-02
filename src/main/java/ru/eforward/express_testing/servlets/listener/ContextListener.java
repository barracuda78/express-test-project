package ru.eforward.express_testing.servlets.listener;

import ru.eforward.express_testing.dao.TestDAO;
import ru.eforward.express_testing.dao.UserDAO;
import ru.eforward.express_testing.model.Student;
import ru.eforward.express_testing.model.User;
import ru.eforward.express_testing.model.UserBuilder;
import ru.eforward.express_testing.model.school.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static ru.eforward.express_testing.model.User.ROLE.*;

/**
 * ContextListener put user dao to servlet context.
 */
@WebListener
public class ContextListener implements ServletContextListener {
    /**
     * Fake database connector.
     */
    private AtomicReference<UserDAO> userDao;
    private AtomicReference<TestDAO> testDao;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        userDao = new AtomicReference<>(new UserDAO());
        testDao = new AtomicReference<>(new TestDAO());

        School schoolEF = new School(1, "EnglishForward");

        List<Branch> branches = new ArrayList<>();
        Branch b1 = new Branch(1, "Парк Победы");
        Branch b2 = new Branch(2, "Горьковская");
        branches.add(b1);
        branches.add(b2);

        TestResult testResult01 = new TestResult(1, "Level01 lesson02",  "I like coffee\nI get up at 6 o'clock\ntable", 10);
        TestResult testResult02 = new TestResult(2, "Level01 lesson03",  "I like to play\nHe has lunch\napple", 9);
        List<TestResult> testResults = new ArrayList<>();
        testResults.add(testResult01);
        testResults.add(testResult02);

        UserBuilder userBuilder = new UserBuilder(STUDENT);
        User user01 = userBuilder
                .addId(1)
                .addLastName("Ruzaev")
                .addFirstName("Andrey")
                .addMiddleName("Vladimirovich")
                .addLogin("barracuda")
                .addPassword("111")
                .addSchool(schoolEF)
                .addBranches(branches)
                .addTestResults(testResults)
                .buildUser();

        userDao.get().addUserToDAO(user01);

        userBuilder = new UserBuilder(ADMIN);
        User user02 = userBuilder
                .addId(2)
                .addLastName("Ivanov")
                .addFirstName("Egor")
                .addMiddleName("Sergeevich")
                .addLogin("Egor")
                .addPassword("1")
                .buildUser();
        userDao.get().addUserToDAO(user02);


        List<Student> students = new ArrayList<>();
        students.add((Student)user01);
        Group group755 = new Group(1, "group755a", students);
        List<Group> groups = new ArrayList<>();
        groups.add(group755);
        groups.add(new Group());
        groups.add(new Group());

        userBuilder = new UserBuilder(TEACHER);
        User user03 = userBuilder
                .addId(3)
                .addLastName("Zinoviev")
                .addFirstName("Pavel")
                .addMiddleName("Fedorovich")
                .addLogin("zin")
                .addPassword("1")
                .addGroupsToTeacher(groups)
                .buildUser();
        userDao.get().addUserToDAO(user03);

        Test test01 = new Test();
        test01.setId(1);
        test01.setName("ENG test: level01 lesson01");
        test01.setActive(false);
        test01.setPath(Paths.get("D:\\coding\\projects\\EF\\express_test_project\\src\\main\\resources\\tests\\eng\\level01\\lesson01.txt"));
        testDao.get().addTestToDAO(test01);

        System.out.println("--->>>---ContextListener: tests = " + testDao.get());

        final ServletContext servletContext = servletContextEvent.getServletContext();

        servletContext.setAttribute("dao", userDao);

        servletContext.setAttribute("test", testDao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        userDao = null;
        testDao = null;
    }
}