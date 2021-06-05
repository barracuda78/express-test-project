package ru.eforward.express_testing.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.eforward.express_testing.model.school.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public abstract class User {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String email;
    private String login;
    private String password;
    private ROLE role;
    private School school;
    private List<Branch> branches;

    public enum ROLE {
        ADMIN,
        TEACHER,
        STUDENT,
        UNKNOWN
    }
}