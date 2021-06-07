package ru.eforward.express_testing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.eforward.express_testing.model.school.Group;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User {
    List<Integer> groups;
}