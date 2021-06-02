package ru.eforward.express_testing.dao;

import ru.eforward.express_testing.model.school.Test;

import java.util.ArrayList;
import java.util.List;

public class TestDAO {
    private final List<Test> testsStore = new ArrayList<>();

    public Test getById(int id) {
        Test result = null;
        //result.setId(-1);
        for (Test test : testsStore) {
            if (test.getId() == id) {
                result = test;
            }
        }
        return result;
    }

    public boolean addTestToDAO(final Test test) {

        for (Test t : testsStore) {
            if (t.getId() == test.getId()) {
                return false;
            }
        }

        return testsStore.add(test);
    }

    public Test getTestById(int id) {
        System.out.println("------------->-----------TestDAO class, getTestById() : we are here");
        Test result = null;

        for (Test t : testsStore) {
            if (t.getId() == id) {
                result = t;
            }
        }

        System.out.println("------------->-----------TestDAO class, getTestById() : result = " + result);
        return result;
    }

}
