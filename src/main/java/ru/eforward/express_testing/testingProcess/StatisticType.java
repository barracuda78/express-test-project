package ru.eforward.express_testing.testingProcess;

import ru.eforward.express_testing.testingProcess.statsRenderers.*;

import java.util.ArrayList;
import java.util.List;

public enum StatisticType {
    ADMIN_GROUP_AVERAGE,
    STUDENT_SINGLE,
    TEACHER_GROUP;

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
