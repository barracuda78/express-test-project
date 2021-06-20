package ru.eforward.express_testing.testingProcess.statsRenderers;

import ru.eforward.express_testing.testingProcess.StatisticType;

import java.util.Collection;

public class TeacherGroupRenderer implements StatsRenderer{
    @Override
    public boolean canRender(StatisticType type) {
        return type == StatisticType.TEACHER_GROUP;
    }

    @Override
    public String render() {
        return null;
    }
}
