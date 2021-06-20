package ru.eforward.express_testing.daoInterfaces;

import ru.eforward.express_testing.model.school.School;

public interface SchoolDAO {
    /**
     * Adds a new school. For further development, SUPER_ADMIN role.
     * @param school - name of a desired school.
     * @return  true if school has been added, otherwise returns false.
     */
    boolean addSchool(School school);

    /**
     * Changes a school name to desired. Is used bu ADMIN .
     * @param schoolName - name of a desired school.
     * @param currentSchoolId - id of this admin's school.
     * @return  true if school name has been changed, otherwise returns false.
     */
    boolean changeSchoolName(String schoolName, int currentSchoolId);

    /**
     * Finds out if school with a such id presents in DB.
     * @param schoolId - id of a target school.
     * @return  true if school exists in DB, otherwise returns false.
     */
    boolean schoolPresents(int schoolId);
//    boolean deleteSchoolById(int id);
//    boolean deleteSchoolByName(String name);
//    boolean changeSchoolNameByName(String oldName, String newName);
}
