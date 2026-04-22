package pro1.reports.report4;

import com.google.gson.Gson;
import pro1.DataSource;
import pro1.apiDataModel.ThesesList;
import pro1.reports.report4.reportDataModel.ThesisDuration;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class ThesisDurationReporting {
    public static ThesisDuration[] GetReport(DataSource dataSource, String katedra, String[] years) {
        return Arrays.stream(years)
                .map(year -> new ThesisDuration(
                        year,
                        averageDuration(dataSource, katedra, year)))
                .toArray(ThesisDuration[]::new);
    }

    private static long averageDuration(DataSource dataSource, String katedra, String year) {
        var thesesJson = dataSource.getKvalifikacniPrace(year, katedra);
        var theses = new Gson().fromJson(thesesJson, ThesesList.class);

        var average = theses.items.stream()
                .filter(t -> t.assignmentDate != null && t.submissionDate != null)
                .filter(t -> t.assignmentDate.isValid() && t.submissionDate.isValid())
                .mapToLong(t -> ChronoUnit.DAYS.between(
                        t.assignmentDate.toLocalDate(),
                        t.submissionDate.toLocalDate()))
                .average()
                .orElse(0);

        return Math.round(average);
    }
}
