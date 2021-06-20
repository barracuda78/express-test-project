package ru.eforward.express_testing.testingProcess.statsRenderers;

import ru.eforward.express_testing.dao.TestResultDAOImpl;
import ru.eforward.express_testing.daoInterfaces.TestResultDAO;
import ru.eforward.express_testing.testingProcess.StatisticType;
import ru.eforward.express_testing.utils.LogHelper;

import java.util.Map;

public class TeacherGroupRenderer implements StatsRenderer{
    @Override
    public boolean canRender(StatisticType type) {
        return type == StatisticType.TEACHER_GROUP;
    }

    @Override
    public <T>String render(T teacherId) {
        int tId = (Integer)teacherId;
        TestResultDAO testResultDAO = new TestResultDAOImpl();
        Map<String, Double> map = testResultDAO.getTeacherGroupAverages(tId); //used for stats like 'groupName : averageScores'

        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
        sb.append("<p>Статистика: средний балл по каждой группе преподавателя:</p>");
        sb.append("<ul>");

        for(Map.Entry<String, Double> pair : map.entrySet()){
            String groupName = pair.getKey();
            Double averageScores = pair.getValue();
            sb.append("<li>");
            sb.append(groupName);
            sb.append(" : ");
            sb.append(averageScores);
            sb.append("</li>");
        }
        sb.append("</ul>");
        sb.append("</p>");

        LogHelper.writeMessage("AdminGroupAverageRenderer: render(): sb.toString() = " + sb.toString());
        return sb.toString();
    }
}
