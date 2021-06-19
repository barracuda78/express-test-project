package ru.eforward.express_testing.testingProcess;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestingUnitTesting {

    @Test
    public void isEveryLineCommentedTesting(){
        TestingUnit testingUnit = new TestingUnit(-1, -1);
        String q = "//abc\n//defg\n//hijk";
        try {
            Method isEveryLineCommented = testingUnit.getClass().getDeclaredMethod("isEveryLineCommented", String.class);
            isEveryLineCommented.setAccessible(true);
            Assertions.assertEquals(true,
                    isEveryLineCommented.invoke(testingUnit, q),
                    "must return true if every line is commented with //");
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}