package au.com.geekseat.theta.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Utility {
    public static final Gson gson = new GsonBuilder() //
            // .disableHtmlEscaping() //
            .create();

    public static boolean isNotBlank(Object object) {
        return object != null && object.toString() != null && !object.toString().isEmpty();
    }

    public static final Type typeMapOfStringObject = new TypeToken<Map<String, Object>>() {
    }.getType();

    public static String querySorting(List<String> columnsName, List<Boolean> sortDesc) {
        if (columnsName.isEmpty()) {
            return "";
        }
        StringBuilder query = new StringBuilder(" order by");
        for (int i = 0; i < columnsName.size(); i++) {
            query.append(" ");
            query.append(columnsName.get(i));
            query.append(" ");
            query.append(sortDesc.isEmpty() ? "asc" : sortDesc.get(i) ? "desc" : "asc");
            query.append(",");
        }
        query.deleteCharAt(query.length() - 1);
        return query.toString();
    }

    public static final DateTimeFormatter fileDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmm");
    public static final DateTimeFormatter fullDateTimeFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
    public static final DateTimeFormatter fullDateTimeReportFormat = DateTimeFormatter.ofPattern("dd'/'MM'/'yyyy");
    public static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
    public static final DateTimeFormatter isoDateTimeFormat = DateTimeFormatter.ISO_DATE_TIME;
    public static final DateTimeFormatter fullDateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    public static final DateTimeFormatter fileDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter monthYearFormat = DateTimeFormatter.ofPattern("MMMM-yyyy");
    public static final DateTimeFormatter shorterMonthYearFormat = DateTimeFormatter.ofPattern("MMM-yy");

    public static final DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static LocalDateTime retrieveDate(Map<String, Object> dataMap, String key, int hour, int minute, int second) {
        if (dataMap != null) {
            String text = (String) dataMap.get(key);
            if (text != null) {
                dataMap.remove(key);
                String[] fields = text.split("-", 3);
                text = fields[0] + "-" + (fields[1].length() == 1 ? "0" : "") + fields[1] + "-" + (fields[2].length() == 1 ? "0" : "") + fields[2];
                LocalDate value = LocalDate.parse(text);
                return value.atTime(hour, minute, second);
            }
        }
        return null;
    }

    public static LocalDateTime retrieveDateStartDay(Map<String, Object> dataMap, String key) {
        return retrieveDate(dataMap, key, 0, 0, 0);
    }

    public static LocalDateTime retrieveDateEndDay(Map<String, Object> dataMap, String key) {
        return retrieveDate(dataMap, key, 23, 59, 59);
    }

    public static <T> T coalesce(T value, T defaultValue) {
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }
}
