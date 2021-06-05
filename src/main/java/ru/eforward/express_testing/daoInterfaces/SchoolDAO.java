package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.school.School;

public interface SchoolDAO {
    boolean addSchool(School school);
    boolean addSchoolByName(String schoolName);
    boolean schoolPresents(int schoolId);
}
