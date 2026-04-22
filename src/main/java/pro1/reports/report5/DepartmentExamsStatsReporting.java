package pro1.reports.report5;

import com.google.gson.Gson;
import pro1.DataSource;
import pro1.apiDataModel.ExamsList;
import pro1.reports.report5.reportDataModel.DepartmentExamsStats;

public class DepartmentExamsStatsReporting {
    public static DepartmentExamsStats GetReport(DataSource dataSource, String katedra) {
        var examsJson = dataSource.getTerminyZkousek2(katedra);
        var exams = new Gson().fromJson(examsJson, ExamsList.class);

        var realizedExamsCount = exams.items.stream()
                .filter(exam -> exam.getStudentsCountValue() > 0)
                .count();

        var teacherIds = exams.items.stream()
                .filter(exam -> exam.teacherId != null)
                .mapToLong(exam -> exam.teacherId)
                .distinct()
                .sorted()
                .toArray();

        return new DepartmentExamsStats(realizedExamsCount, teacherIds);
    }
}
