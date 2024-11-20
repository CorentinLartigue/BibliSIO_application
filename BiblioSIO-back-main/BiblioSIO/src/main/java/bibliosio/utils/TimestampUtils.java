package bibliosio.utils;

import java.sql.Timestamp;
import java.util.Objects;

public class TimestampUtils {
    public static Boolean isEquals(Timestamp timestamp1, Timestamp timestamp2) {
        return (timestamp1 != null && timestamp2 !=null) ?
                timestamp1.getTime()==timestamp2.getTime() && timestamp1.getNanos()-timestamp2.getNanos()<1000000
                : Objects.equals(timestamp1, timestamp2);
    }
}
