package pro1.apiDataModel;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

public class ApiDate {
    private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("d.M.uuuu")
            .toFormatter();

    @SerializedName("value")
    public String value;

    public boolean isValid() {
        if (value == null || value.isBlank()) {
            return false;
        }
        try {
            LocalDate.parse(value.trim(), DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    public LocalDate toLocalDate() {
        return LocalDate.parse(value.trim(), DATE_FORMATTER);
    }
}
