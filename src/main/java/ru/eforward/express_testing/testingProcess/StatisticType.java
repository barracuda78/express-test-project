package ru.eforward.express_testing.testingProcess;

import ru.eforward.express_testing.testingProcess.statsRenderers.*;

import java.util.ArrayList;
import java.util.List;

public enum StatisticType {
    ADMIN_GROUP_AVERAGE,
    STUDENT_SINGLE,
    TEACHER_GROUP;

    /**
     * Returns an appropriate StatsRenderer interface implementation for the given type of StatisticType.
     * StatsRenderers are used to render different statistics to appropriate HTML-formatted string;
     * @param type - given type of StatisticType.
     * @return an appropriate StatsRenderer interface implementation for the given type of StatisticType.
     */
    public static StatsRenderer getStatsRenderer(StatisticType type){
        //todo: implement package scanning here for automatic adding of entities from package enumHandlers.
        List<StatsRenderer> renderers = new ArrayList<>();
        renderers.add(new AdminGroupAverageRenderer());
        renderers.add(new StudentSingleRenderer());
        renderers.add(new TeacherGroupRenderer());

        for(StatsRenderer r : renderers){
            if(r.canRender(type)){
                return r;
            }
        }
        return new UndefinedRenderer();
    }
}
