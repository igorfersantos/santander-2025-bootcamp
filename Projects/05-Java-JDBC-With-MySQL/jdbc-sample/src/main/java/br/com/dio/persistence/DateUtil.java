package br.com.dio.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static java.time.ZoneOffset.UTC;

public class DateUtil {
    /**
     * Converts an {@link OffsetDateTime} object into a MySQL DATE string in the format
     * "yyyy-MM-dd HH:mm:ss".
     *
     * @param date The OffsetDateTime object to be converted.
     * @return A String representation of the given OffsetDateTime object.
     */
    public static String formatOffsetDateTime(final OffsetDateTime date) {
        var dateTime = date.withOffsetSameInstant(UTC);
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Retrieves a {@link OffsetDateTime} from a {@link ResultSet} column, or null if the column is null.
     *
     * @param resultSet The {@link ResultSet} to retrieve the value from.
     * @param field     The name of the column to retrieve the value from.
     * @return An {@link OffsetDateTime} from the column in the given {@link ResultSet}, or null if the column is null.
     * @throws SQLException If there is an error retrieving the value from the {@link ResultSet}.
     */
    public static OffsetDateTime getDateTimeOrNull(final ResultSet resultSet, final String field) throws SQLException {
        final Timestamp birthday = resultSet.getTimestamp(field);
        return Objects.isNull(birthday) ? null :
                OffsetDateTime.ofInstant(birthday.toInstant(), UTC);
    }
}
