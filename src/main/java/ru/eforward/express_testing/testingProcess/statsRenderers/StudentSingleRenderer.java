package ru.eforward.express_testing.testingProcess.statsRenderers;

import ru.eforward.express_testing.dao.TestResultDAOImpl;
import ru.eforward.express_testing.daoInterfaces.TestResultDAO;
import ru.eforward.express_testing.testingProcess.StatisticType;
import ru.eforward.express_testing.utils.LogHelper;

import java.util.Map;

public class StudentSingleRenderer implements StatsRenderer{
    @Override
    public boolean canRender(StatisticType type) {
        return type == StatisticType.STUDENT_SINGLE;
    }

    @Override
    public <T>String render(T studentId) {
        int sId = (Integer)studentId;
        TestResultDAO testResultDAO = new TestResultDAOImpl();
        Map<String, Double> map = testResultDAO.getStudentStats(sId); //used for stats like 'LessonName : score'

        LogHelper.writeMessage("StudentSingleRenderer: render(): map = " + map);

        StringBuilder sb = new StringBuilder();
        sb.append("<p>");
        sb.append("<p>Статистика: Урок - Балл:</p>");
        sb.append("<ul>");

        for(Map.Entry<String, Double> pair : map.entrySet()){
            String lessonName = pair.getKey();
            Double score = pair.getValue();
            sb.append("<li>");
            sb.append(lessonName);
            sb.append(" : ");
            sb.append(score);
            sb.append("</li>");
        }
        sb.append("</ul>");
        sb.append("</p>");


        LogHelper.writeMessage("StudentSingleRenderer: render(): sb.toString() = " + sb.toString());
        return sb.toString();
    }
}
