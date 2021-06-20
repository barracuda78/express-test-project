package ru.eforward.express_testing.testingProcess.statsRenderers;

import ru.eforward.express_testing.dao.TestResultDAOImpl;
import ru.eforward.express_testing.daoInterfaces.TestResultDAO;
import ru.eforward.express_testing.testingProcess.StatisticType;
import ru.eforward.express_testing.utils.LogHelper;

import java.util.Map;

public class AdminGroupAverageRenderer implements StatsRenderer{

    @Override
    public boolean canRender(StatisticType type) {
        return type == StatisticType.ADMIN_GROUP_AVERAGE;
    }

    @Override
    public <T>String render(T stub) {
        TestResultDAO testResultDAO = new TestResultDAOImpl();
        Map<String, Double> map = testResultDAO.getGroupAverages(); //used for stats like 'groupName : averageScores'

        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
        sb.append("<p>Статистика: средний балл по каждой группе школы:</p>");
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
