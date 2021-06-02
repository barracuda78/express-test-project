package ru.eforward.express_testing.model.school;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

@Getter
@Setter
public class Test {
    private int id;
    private String name;
    private Path path;
}
