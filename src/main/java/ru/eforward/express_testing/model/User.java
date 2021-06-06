package ru.eforward.express_testing.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.eforward.express_testing.model.school.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    private List<Integer> branches; //contains branch_id's



    public enum ROLE {
        ADMIN(1),
        TEACHER(2),
        STUDENT(3),
        UNKNOWN(4);

        private final int id;

        ROLE(int id){
            this.id = id;
        }

        public int getId(){
            return id;
        }
        public static ROLE getRoleById(int id){
            Optional<ROLE> firstRole = Arrays.stream(values()).filter(role -> role.id == id).findFirst();
            return firstRole.orElse(UNKNOWN);
        }
    }
}