package ru.eforward.express_testing.model;

public class User {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String login;
    private String password;
    private ROLE role;
    //school
    //branch
    //course
    //level
    //lesson
    //testResult
    //group
    //teacher

    public User() {
    }

    public User(int id, String login, String password, ROLE role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public enum ROLE {
        USER, ADMIN, TEACHER, UNKNOWN
    }
}
