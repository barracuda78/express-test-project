package ru.eforward.express_testing.testingProcess.statsRenderers;

import ru.eforward.express_testing.testingProcess.StatisticType;

import java.util.Collection;

public class StudentSingleRenderer implements StatsRenderer{
    @Override
    public boolean canRender(StatisticType type) {
        return type == StatisticType.STUDENT_SINGLE;
    }

    @Override
    public String render() {
        return null;
    }
}
