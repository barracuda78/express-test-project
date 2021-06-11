package ru.eforward.express_testing.model.school;

import ru.eforward.express_testing.model.Admin;
import ru.eforward.express_testing.model.Student;
import ru.eforward.express_testing.model.Teacher;

import java.util.List;

public class School {
    private int id;
    private String name;
    private List<Branch> branches;
    private List<Course> courses;
    private List<Teacher> teachers;
    private List<Admin> admins;
    private List<Student> students;

    public School() {

    }

    public School(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    @Override
    public String toString() {
        return "Школа: "  + name;
    }
}
