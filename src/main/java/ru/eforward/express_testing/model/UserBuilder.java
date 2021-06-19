package ru.eforward.express_testing.model;

import ru.eforward.express_testing.model.school.*;

import java.util.List;

public class UserBuilder {
    private User user;
    private User.ROLE role;
//    private Student student;
//    private Admin admin;
//    private Teacher teacher;

    public UserBuilder(User.ROLE role){
        this.role = role;

        switch(role){
            case STUDENT:
                user = new Student();
                break;
            case TEACHER:
                user = new Teacher();
                break;
            case ADMIN:
                user = new Admin();
                break;
            case UNKNOWN:
                user = null;
                break;
        }

        if(user != null) {
            user.setRole(role);
        }

    }

    public UserBuilder addId(int id){
        user.setId(id);
        return this;
    }

    public UserBuilder addLastName(String lastName){
        user.setLastName(lastName);
        return this;
    }

    public UserBuilder addFirstName(String firstName){
        user.setFirstName(firstName);
        return this;
    }

    public UserBuilder addMiddleName(String middleName){
        user.setMiddleName(middleName);
        return this;
    }

    public UserBuilder addLogin(String login){
        user.setLogin(login);
        return this;

    }

    public UserBuilder addEmail(String email){
        user.setEmail(email);
        return this;
    }

    public UserBuilder addPassword(String password){
        user.setPassword(password);
        return this;
    }

    public UserBuilder addSchool(int school_id){
        user.setSchool(school_id);
        return this;
    }

    public UserBuilder addBranch(int branchId){
        user.setBranch(branchId);
        return this;
    }

    public UserBuilder addCurator(int curatorId){
        user.setCuratorId(curatorId);
        return this;
    }

    //методы только для студента:

    public UserBuilder addTestResults(List<Integer> testResults){
        ((Student)user).setTestResults(testResults);
        return this;
    }

    public UserBuilder addGroupIdToStudent(int groupId) {
        ((Student)user).setGroupId(groupId);
        return this;
    }


    //------------------------------

    //методы только для преподавателя:
    public UserBuilder addGroupsToTeacher(List<Integer> groups){
        ((Teacher)user).setGroups(groups);
        return this;
    }

    //терминальный метод:
    public <T extends User>T buildUser(){
        return (T) user;
    }


}
