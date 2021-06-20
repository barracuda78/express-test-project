package ru.eforward.express_testing.testingProcess.statsRenderers;

import ru.eforward.express_testing.testingProcess.StatisticType;

import java.util.Collection;

public interface StatsRenderer {
    /**
     *Takes a StatisticType enum value, finds out if this value can be processed with this class.
     * @param type - a StatisticType enum value.
     * @return true if this value can be processed with this class, otherwise returns false;
     */
    boolean canRender(StatisticType type);

    /**
     *Renders a given statistic information to HTML-formatted string;
     * @return HTML-formatted string of statistic info;
     */
    String render();
}
