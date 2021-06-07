package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.school.School;

public interface SchoolDAO {
    boolean addSchool(School school);
    boolean changeSchoolName(String schoolName, int currentSchoolId);
    boolean schoolPresents(int schoolId);
    boolean deleteSchoolById(int id);
    boolean deleteSchoolByName(String name);
    boolean changeSchoolNameByName(String oldName, String newName);
}
