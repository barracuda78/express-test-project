package ru.eforward.express_testing.testingProcess.statsRenderers;

import ru.eforward.express_testing.testingProcess.StatisticType;

public class UndefinedRenderer implements StatsRenderer{
    @Override
    public boolean canRender(StatisticType type) {
        return false;
    }

    @Override
    public String render(Object stub) {
        return "<p>Нет статистики</p>";
    }
}
