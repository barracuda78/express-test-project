package ru.eforward.express_testing.model.school;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class School {
    private int id;
    private String name;
    private List<Branch> branches;

    public School(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Школа: "  + name;
    }
}
