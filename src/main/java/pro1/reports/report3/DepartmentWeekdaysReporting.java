package pro1.reports.report3;

import com.google.gson.Gson;
import pro1.DataSource;
import pro1.apiDataModel.ActionsList;
import pro1.reports.report3.reportDataModel.WeekdayStat;

import java.text.Normalizer;
import java.util.Arrays;

public class DepartmentWeekdaysReporting {
    public static WeekdayStat[] GetReport(DataSource dataSource, String rok, String katedra, String[] days) {
        var actionsJson = dataSource.getRozvrhByKatedra(rok, katedra);
        var actionsList = new Gson().fromJson(actionsJson, ActionsList.class);

        return Arrays.stream(days)
                .map(day -> new WeekdayStat(
                        displayDay(day),
                        actionsList.items.stream()
                                .filter(a -> isSameDay(day, a.weekday))
                                .count()))
                .toArray(WeekdayStat[]::new);
    }

    private static boolean isSameDay(String requestedDay, String actionDay) {
        return normalizeDay(requestedDay).equals(normalizeDay(actionDay));
    }

    private static String normalizeDay(String day) {
        if (day == null) {
            return "";
        }

        var normalized = Normalizer.normalize(day, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .trim();

        if (normalized.startsWith("po")) {
            return "po";
        }
        if (normalized.startsWith("ut") || normalized.contains("гљt")) {
            return "ut";
        }
        if (normalized.startsWith("st")) {
            return "st";
        }
        if (normalized.startsWith("ct")) {
            return "ct";
        }
        if (normalized.startsWith("pa")) {
            return "pa";
        }
        return normalized;
    }

    private static String displayDay(String day) {
        return switch (normalizeDay(day)) {
            case "po" -> "Po";
            case "ut" -> "Út";
            case "st" -> "St";
            case "ct" -> "Čt";
            case "pa" -> "Pá";
            default -> day;
        };
    }
}
