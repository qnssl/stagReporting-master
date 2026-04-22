package pro1.reports.report2;

import com.google.gson.Gson;
import pro1.DataSource;
import pro1.apiDataModel.ActionsList;
import pro1.reports.report2.reportDataModel.DepartmentStats;

public class DepartmentStatsReporting {
    public static DepartmentStats GetReport(DataSource dataSource, String rok, String katedra) {
        var actionsListJson = dataSource.getRozvrhByKatedra(rok, katedra);
        var actionsList = new Gson().fromJson(actionsListJson, ActionsList.class);
        return new DepartmentStats(
                maxActionStudentsCount(actionsList),
                emptyActionsCount(actionsList),
                maxTeacherScore(actionsList)
        );
    }

    private static long maxActionStudentsCount(ActionsList actionsList) {
        var result = actionsList.items.stream()
                .mapToLong(a -> a.studentsCount)
                .max()
                .orElse(-1);
        return result;
    }

    private static long emptyActionsCount(ActionsList actionsList) {
        var result = actionsList.items.stream()
                .filter(a -> a.studentsCount == 0)
                .count();
        return result;
    }

    private static long maxTeacherScore(ActionsList actionsList) {
        var teacherIds = actionsList.items.stream()
                .mapToLong(a -> a.teacherId)
                .distinct();
        var max = teacherIds
                .map(id -> teacherScore(id, actionsList))
                .max()
                .orElse(-1);
        return max;
    }

    private static long teacherScore(long teacherId, ActionsList actionsList) {
        var result = actionsList.items.stream()
                .filter(a -> a.teacherId == teacherId)
                .mapToLong(a -> a.studentsCount)
                .sum();
        return result;
    }
}
