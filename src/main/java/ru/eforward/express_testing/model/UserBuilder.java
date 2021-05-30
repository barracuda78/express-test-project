package ru.eforward.express_testing.model;

public class UserBuilder {
    private User user;

    public UserBuilder(){
        user = new User();
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

    public UserBuilder addPassword(String password){
        user.setPassword(password);
        return this;
    }

    public UserBuilder addRole(User.ROLE role){
        user.setRole(role);
        return this;
    }

    public User buildUser(){
        return user;
    }
}
